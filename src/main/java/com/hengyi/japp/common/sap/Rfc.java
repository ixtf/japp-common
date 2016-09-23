package com.hengyi.japp.common.sap;

import com.sap.conn.jco.*;
import org.apache.commons.collections4.IterableUtils;

import java.util.List;

import static com.hengyi.japp.common.J_sap.convert;
import static com.hengyi.japp.common.J_sap.copy;
import static com.hengyi.japp.common.sap.SapClient.SAP_FALSE;
import static com.hengyi.japp.common.sap.SapClient.SAP_TRUE;

/**
 * Created by jzb on 16-5-24.
 */
public class Rfc {
    private final JCoDestination dest;
    private final JCoFunction f;

    Rfc(JCoDestination dest, JCoFunction f) {
        this.dest = dest;
        this.f = f;
    }

    public Rfc setImport(String field, Object value) {
        JCoParameterList paramList = f.getImportParameterList();
        _set(paramList, field, value);
        return this;
    }

    public Rfc setChanging(String field, Object value) {
        JCoParameterList paramList = f.getChangingParameterList();
        _set(paramList, field, value);
        return this;
    }

    public Rfc setTable(String field, Object values) {
        JCoParameterList paramList = f.getTableParameterList();
        _set(paramList, field, values);
        return this;
    }

    public Rfc exe() throws JCoException {
        f.execute(dest);
        return this;
    }

    public <T> T getExport(String field) {
        return (T) f.getExportParameterList().getValue(field);
    }

    public <T> T getExport(String field, Class<T> clazz) throws InstantiationException, IllegalAccessException {
        JCoParameterList paramList = f.getExportParameterList();
        return _get(paramList, field, clazz);
    }

    public <T> T getChanging(String field) {
        return (T) f.getChangingParameterList().getValue(field);
    }

    public <T> T getChanging(String field, Class<T> clazz) throws InstantiationException, IllegalAccessException {
        JCoParameterList paramList = f.getChangingParameterList();
        return _get(paramList, field, clazz);
    }

    public JCoTable getTable(String field) {
        return f.getTableParameterList().getTable(field);
    }

    public <T> List<T> getTable(String field, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        return convert(f.getTableParameterList().getTable(field), clazz);
    }

    private void _set(JCoParameterList paramList, String field, Object value) {
        JCoMetaData metaData = paramList.getMetaData();
        switch (metaData.getType(field)) {
            case JCoMetaData.TYPE_TABLE:
                JCoTable t = paramList.getTable(field);
                IterableUtils.forEach((Iterable<?>) value, o -> {
                    t.appendRow();
                    JCoRecord r = t;
                    copy(r, o);
                });
                break;
            case JCoMetaData.TYPE_STRUCTURE:
                copy(paramList.getStructure(field), value);
                break;
            default:
                if (value instanceof Boolean) {
                    Boolean b = (Boolean) value;
                    paramList.setValue(field, b ? SAP_TRUE : SAP_FALSE);
                } else {
                    paramList.setValue(field, value);
                }
                break;
        }
    }

    private <T> T _get(JCoParameterList paramList, String field, Class<?> clazz) throws InstantiationException, IllegalAccessException {
        JCoMetaData metaData = paramList.getMetaData();
        switch (metaData.getType(field)) {
            case JCoMetaData.TYPE_TABLE:
                return (T) convert(paramList.getTable(field), clazz);
            case JCoMetaData.TYPE_STRUCTURE:
                return (T) convert(paramList.getStructure(field), clazz);
            default:
                return (T) paramList.getValue(field);
        }
    }

}
