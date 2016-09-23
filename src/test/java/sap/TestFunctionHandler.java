package sap;

import com.hengyi.japp.common.sap.annotation.SapFunctionHandler;
import com.sap.conn.jco.AbapClassException;
import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.server.JCoServerContext;
import com.sap.conn.jco.server.JCoServerFunctionHandler;

/**
 * Created by jzb on 16-8-20.
 */
@SapFunctionHandler(functionName = "STFC_CONNECTION")
public class TestFunctionHandler implements JCoServerFunctionHandler {
    @Override
    public void handleRequest(JCoServerContext ctx, JCoFunction f) throws AbapException, AbapClassException {
        String requtext = f.getImportParameterList().getString("REQUTEXT");
        System.out.println(requtext);
        f.getExportParameterList().setValue("ECHOTEXT", requtext);
        f.getExportParameterList().setValue("RESPTEXT", this.getClass().getName());
    }
}
