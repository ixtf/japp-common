package com.hengyi.japp.common.sap.convert;

import com.sap.conn.jco.JCoRecord;

public interface IFieldCopy<T> extends IValueCopy<T> {
    @Override
    default void copy(T dest, JCoRecord source) {
        setBeanValue(dest, getSapValue(source));
    }

    @Override
    default void copy(JCoRecord dest, T source) {
        setSapValue(dest, getBeanValue(source));
    }

    Object getBeanValue(T bean);

    void setBeanValue(T bean, Object value);

    Object getSapValue(JCoRecord record);

    void setSapValue(JCoRecord record, Object value);
}
