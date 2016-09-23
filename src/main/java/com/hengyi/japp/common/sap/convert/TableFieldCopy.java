package com.hengyi.japp.common.sap.convert;

import com.google.common.collect.Sets;
import com.hengyi.japp.common.J_sap;
import com.sap.conn.jco.JCoRecord;
import com.sap.conn.jco.JCoTable;
import org.apache.commons.lang3.Validate;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by jzb on 16-5-24.
 */
public class TableFieldCopy<T> extends BaseFieldCopy<T> {
    private final Class targetClass;

    public TableFieldCopy(String beanFieldName, Method beanReadMethod, Method beanWriteMethod, String sapFieldName, Class targetClass) {
        super(beanFieldName, beanReadMethod, beanWriteMethod, sapFieldName);
        Validate.notNull(targetClass);
        this.targetClass = targetClass;
    }

    @Override
    public void setBeanValue(T bean, Object value) {
        JCoTable table = (JCoTable) value;
        List list = J_sap.convert(table, targetClass);

        Class clazz = beanWriteMethod.getParameterTypes()[0];
        if (clazz.isAssignableFrom(Set.class))
            super.setBeanValue(bean, Sets.newHashSet(list));
        else
            super.setBeanValue(bean, list);
    }

    @Override
    public void setSapValue(JCoRecord r, Object value) {
        JCoTable table = r.getTable(sapFieldName);
        J_sap.copy(table, (Collection) value);
        super.setSapValue(r, table);
    }
}
