package com.hengyi.japp.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hengyi.japp.common.sap.annotation.SapConvertField;
import com.hengyi.japp.common.sap.annotation.SapConvertTableField;
import com.hengyi.japp.common.sap.annotation.SapTransient;
import com.hengyi.japp.common.sap.convert.*;
import com.sap.conn.jco.JCoMetaData;
import com.sap.conn.jco.JCoRecord;
import com.sap.conn.jco.JCoTable;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class J_sap {
    private static final LoadingCache<Class, Map<JCoMetaData, IValueCopy>> cache = CacheBuilder.newBuilder().build(
            new CacheLoader<Class, Map<JCoMetaData, IValueCopy>>() {
                @Override
                public Map<JCoMetaData, IValueCopy> load(Class key) throws Exception {
                    return Maps.newConcurrentMap();
                }
            });

    public static final <T> T convert(JCoRecord r, Class<T> clazz) {
        try {
            T t = clazz.newInstance();
            copy(t, r);
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static final <T> List<T> convert(JCoTable t, Class<T> clazz) {
        int numRows = t.getNumRows();
        if (numRows < 1)
            return Collections.EMPTY_LIST;

        List<T> result = Lists.newArrayListWithExpectedSize(numRows);
        t.firstRow();
        do {
            JCoRecord r = t;
            result.add(convert(r, clazz));
        } while (t.nextRow());
        return result;
    }

    /**
     * 复制
     * o --> r
     */
    public static final void copy(JCoRecord dest, Object source) {
        getValueCopy(source.getClass(), dest.getMetaData()).copy(dest, source);
    }

    /**
     * 复制
     * r --> o
     */
    public static final void copy(Object dest, JCoRecord source) {
        getValueCopy(dest.getClass(), source.getMetaData()).copy(dest, source);
    }

    public static void copy(JCoTable dest, Collection sources) {
        sources.forEach(source -> {
            dest.appendRow();
            JCoRecord r = dest;
            copy(r, source);
        });
    }

    private static IValueCopy getValueCopy(Class beanClass, JCoMetaData metaData) {
        try {
            IValueCopy result = cache.get(beanClass).get(metaData);
            return result != null ? result : newValueCopy(beanClass, metaData);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static synchronized IValueCopy newValueCopy(Class beanClass, JCoMetaData metaData) {
        try {
            IValueCopy result = cache.get(beanClass).get(metaData);
            if (result != null) {
                return result;
            }

            ImmutableSet.Builder<IFieldCopy> builder = ImmutableSet.builder();
            for (PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(beanClass)) {
                IFieldCopy fieldCopy = newFieldCopy(beanClass, metaData, pd);
                if (fieldCopy != null)
                    builder.add(fieldCopy);
            }
            result = new ValueCopy(beanClass, metaData, builder.build());
            cache.get(beanClass).put(metaData, result);
            return result;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static IFieldCopy newFieldCopy(Class beanClass, JCoMetaData metaData, PropertyDescriptor pd) {
        String beanFieldName = pd.getName();
        Field beanField = null;
        try {
            beanField = beanClass.getDeclaredField(beanFieldName);
            if (beanField.getAnnotation(SapTransient.class) != null)
                return null;
        } catch (NoSuchFieldException e) {
            //TODO 属性未命名，但可能有get set 方法
        }

        Method beanReadMethod = pd.getReadMethod();
        if (beanReadMethod != null && beanReadMethod.getAnnotation(SapTransient.class) != null)
            beanReadMethod = null;
        Method beanWriteMethod = pd.getWriteMethod();
        if (beanWriteMethod != null && beanWriteMethod.getAnnotation(SapTransient.class) != null)
            beanWriteMethod = null;
        if (beanReadMethod == null && beanWriteMethod == null)
            return null;

        SapConvertField sapConvertField = getAnnotation(beanReadMethod, beanWriteMethod, beanField, SapConvertField.class);
        String sapFieldName = sapConvertField == null ? StringUtils.upperCase(beanFieldName) : sapConvertField.value();
        if (!metaData.hasField(sapFieldName))
            return null;
        SapConvertTableField sapConvertTableField = getAnnotation(beanReadMethod, beanWriteMethod, beanField, SapConvertTableField.class);
        if (sapConvertTableField != null) {
            return new TableFieldCopy(beanFieldName, beanReadMethod, beanWriteMethod, sapFieldName, sapConvertTableField.targetClass());
        }
        if (JCoMetaData.TYPE_STRUCTURE == metaData.getType(sapFieldName)) {
            return new StructureFieldCopy(beanFieldName, beanReadMethod, beanWriteMethod, sapFieldName);
        }
        Class beanFieldType = beanReadMethod != null ? beanReadMethod.getReturnType() : beanWriteMethod.getParameterTypes()[0];
        if (beanFieldType.isAssignableFrom(Boolean.class) || beanFieldType.isAssignableFrom(boolean.class)) {
            return new BooleanFieldCopy(beanFieldName, beanReadMethod, beanWriteMethod, sapFieldName);
        }
        return new BaseFieldCopy(beanFieldName, beanReadMethod, beanWriteMethod, sapFieldName);
    }

    private static <T extends Annotation> T getAnnotation(Method beanReadMethod, Method beanWriteMethod, Field beanField, Class<T> annotationClass) {
        T result = null;
        if (beanReadMethod != null) {
            result = beanReadMethod.getAnnotation(annotationClass);
            if (result != null)
                return result;
        }
        if (beanWriteMethod != null) {
            result = beanWriteMethod.getAnnotation(annotationClass);
            if (result != null)
                return result;
        }
        if (beanField != null) {
            result = beanField.getAnnotation(annotationClass);
            if (result != null)
                return result;
        }
        return null;
    }
}
