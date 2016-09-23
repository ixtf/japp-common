package com.hengyi.japp.common.sap.convert;

import com.sap.conn.jco.JCoMetaData;
import com.sap.conn.jco.JCoRecord;

import java.util.Collection;

/**
 * Created by jzb on 16-7-5.
 */
public class ValueCopy<T> implements IValueCopy<T> {
    private final Class<T> beanClass;
    private final JCoMetaData metaData;
    private final Collection<IFieldCopy<T>> fieldCopys;

    public ValueCopy(Class<T> beanClass, JCoMetaData metaData, Collection<IFieldCopy<T>> fieldCopys) {
        this.beanClass = beanClass;
        this.metaData = metaData;
        this.fieldCopys = fieldCopys;
    }

    @Override
    public void copy(T bean, JCoRecord record) {
        fieldCopys.parallelStream().forEach(fc -> fc.copy(bean, record));
    }

    @Override
    public void copy(JCoRecord record, T bean) {
        fieldCopys.parallelStream().forEach(fc -> fc.copy(record, bean));
    }

    public Class<T> getBeanClass() {
        return beanClass;
    }

    public JCoMetaData getMetaData() {
        return metaData;
    }

    public Collection<IFieldCopy<T>> getFieldCopys() {
        return fieldCopys;
    }
}
