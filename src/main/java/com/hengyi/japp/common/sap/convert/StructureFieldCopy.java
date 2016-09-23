package com.hengyi.japp.common.sap.convert;

import com.hengyi.japp.common.J_sap;
import com.sap.conn.jco.JCoRecord;
import com.sap.conn.jco.JCoStructure;

import java.lang.reflect.Method;

/**
 * Created by jzb on 16-5-24.
 */
public class StructureFieldCopy<T> extends BaseFieldCopy<T> {
    public StructureFieldCopy(String beanFieldName, Method beanReadMethod, Method beanWriteMethod, String sapFieldName) {
        super(beanFieldName, beanReadMethod, beanWriteMethod, sapFieldName);
    }

    @Override
    public void setBeanValue(T bean, Object value) {
        JCoRecord record = (JCoStructure) value;
        Class fieldType = beanWriteMethod.getParameterTypes()[0];
        super.setBeanValue(bean, J_sap.convert(record, fieldType));
    }

    @Override
    public void setSapValue(JCoRecord r, Object value) {
        JCoStructure structure = r.getStructure(sapFieldName);
        J_sap.copy(structure, value);
        super.setSapValue(r, structure);
    }
}
