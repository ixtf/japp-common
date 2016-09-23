package com.hengyi.japp.common.sap.convert;

import com.sap.conn.jco.JCoRecord;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by jzb on 16-5-24.
 */
public class BaseFieldCopy<T> implements IFieldCopy<T>, IValueCopy<T> {
    protected final String beanFieldName;
    protected final Method beanReadMethod;
    protected final Method beanWriteMethod;
    protected final String sapFieldName;

    public BaseFieldCopy(String beanFieldName, Method beanReadMethod, Method beanWriteMethod, String sapFieldName) {
        this.beanFieldName = beanFieldName;
        this.beanReadMethod = beanReadMethod;
        this.beanWriteMethod = beanWriteMethod;
        this.sapFieldName = sapFieldName;
    }

    @Override
    public void copy(T dest, JCoRecord source) {
        if (beanWriteMethod == null)
            return;
        IFieldCopy.super.copy(dest, source);
    }

    @Override
    public void copy(JCoRecord dest, T source) {
        if (beanReadMethod == null)
            return;
        IFieldCopy.super.copy(dest, source);
    }

    @Override
    public Object getBeanValue(T bean) {
        try {
            return beanReadMethod.invoke(bean, null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setBeanValue(T bean, Object value) {
        try {
            beanWriteMethod.invoke(bean, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object getSapValue(JCoRecord r) {
        return r.getValue(sapFieldName);
    }

    @Override
    public void setSapValue(JCoRecord r, Object value) {
        r.setValue(sapFieldName, value);
    }

    public String getBeanFieldName() {
        return beanFieldName;
    }

    public Method getBeanReadMethod() {
        return beanReadMethod;
    }

    public Method getBeanWriteMethod() {
        return beanWriteMethod;
    }

    public String getSapFieldName() {
        return sapFieldName;
    }
}
