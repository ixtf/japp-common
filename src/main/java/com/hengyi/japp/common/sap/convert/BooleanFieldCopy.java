package com.hengyi.japp.common.sap.convert;

import com.sap.conn.jco.JCoRecord;

import java.lang.reflect.Method;
import java.util.Objects;

import static com.hengyi.japp.common.sap.SapClient.SAP_FALSE;
import static com.hengyi.japp.common.sap.SapClient.SAP_TRUE;

/**
 * Created by jzb on 16-5-24.
 */
public class BooleanFieldCopy<T> extends BaseFieldCopy<T> {
    public BooleanFieldCopy(String beanFieldName, Method beanReadMethod, Method beanWriteMethod, String sapFieldName) {
        super(beanFieldName, beanReadMethod, beanWriteMethod, sapFieldName);
    }

    @Override
    public void setBeanValue(T bean, Object value) {
        super.setBeanValue(bean, Objects.equals(value, SAP_TRUE));
    }

    @Override
    public void setSapValue(JCoRecord r, Object value) {
        Boolean b = (Boolean) value;
        super.setSapValue(r, b ? SAP_TRUE : SAP_FALSE);
    }
}
