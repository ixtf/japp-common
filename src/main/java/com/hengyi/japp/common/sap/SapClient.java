package com.hengyi.japp.common.sap;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.hengyi.japp.common.sap.annotation.SapFunctionHandler;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.ext.Environment;
import com.sap.conn.jco.server.DefaultServerHandlerFactory.FunctionHandlerFactory;
import com.sap.conn.jco.server.JCoServer;
import com.sap.conn.jco.server.JCoServerFactory;
import com.sap.conn.jco.server.JCoServerFunctionHandler;
import com.sap.conn.jco.server.JCoServerState;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class SapClient {
    public static final String SAP_TRUE = "X";
    public static final String SAP_FALSE = "";
    private static final LoadingCache<DestinationType, SapClient> cache = CacheBuilder.newBuilder()
            .build(new CacheLoader<DestinationType, SapClient>() {

                @Override
                public SapClient load(DestinationType key) throws Exception {
                    return new SapClient(key);
                }
            });
    private static final Logger log = LoggerFactory.getLogger(SapClient.class);

    static {
        if (!Environment.isDestinationDataProviderRegistered()) {
            DataProvider dataProvider = new DataProvider();

            System.out.println("========注册SAP DESTINATION========");
            Environment.registerDestinationDataProvider(dataProvider);

            System.out.println("========注册SAP SERVER========");
            Environment.registerServerDataProvider(dataProvider);
        }
    }

    private final DestinationType destinationType;

    private SapClient(DestinationType destinationType) {
        this.destinationType = destinationType;
    }

    public static final SapClient instance(DestinationType type) {
        try {
            return cache.get(type);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public Rfc rfc(String fName) throws JCoException {
        JCoDestination dest = dest();
        JCoFunction f = dest.getRepository().getFunctionTemplate(fName).getFunction();
        return new Rfc(dest, f);
    }

    public void registerFunctionHandler(JCoServerFunctionHandler handler) {
        getFunctionName(handler).ifPresent(fName -> registerFunctionHandler(fName, handler));
    }

    public void registerFunctionHandler(final String fName, JCoServerFunctionHandler handler) {
        registerFunctionHandler(server(), fName, handler);
    }

    public void removeFunctionHandler(JCoServerFunctionHandler handler) {
        getFunctionName(handler).ifPresent(this::removeFunctionHandler);
    }

    private void removeFunctionHandler(String fName) {
        Optional.ofNullable(server().getCallHandlerFactory()).ifPresent(f -> {
            FunctionHandlerFactory factory = (FunctionHandlerFactory) f;
            factory.removeHandler(fName);
            System.out.println("移除Sap Function Handler：" + fName);
        });
    }

    private Optional<String> getFunctionName(JCoServerFunctionHandler handler) {
        SapFunctionHandler annotation = handler.getClass().getAnnotation(SapFunctionHandler.class);
        return Optional.ofNullable(annotation).map(a -> a.functionName());
    }

    private void registerFunctionHandler(JCoServer server, String fName, JCoServerFunctionHandler handler) {
        Validate.notBlank(fName);
        Validate.notNull(handler);
        FunctionHandlerFactory factory = (FunctionHandlerFactory) server.getCallHandlerFactory();
        if (factory == null) {
            factory = new FunctionHandlerFactory();
            server.setCallHandlerFactory(factory);
        }
        factory.registerHandler(fName, handler);
        start(server);
        System.out.println("注册Sap Function：" + fName + ",Class:" + handler.getClass().getName());
    }

    public final JCoDestination dest() throws JCoException {
        return JCoDestinationManager.getDestination(destinationType.name());
    }

    private JCoServer server() {
        try {
            return JCoServerFactory.getServer(destinationType.name());
        } catch (JCoException e) {
            log.error("获取SAP SERVER出错", e);
            throw new RuntimeException(e);
        }
    }

    private void start(JCoServer server) {
        if (JCoServerState.STARTED.equals(server.getState())) {
            return;
        }
        server.start();
        System.out.println("========启动SAP SERVER========");
    }

//    SmsSendResponseDTO sendSms(SmsSendDTO smsSend) throws Exception;
//
//    String convertKunnr(String kunnr) throws Exception;
//
//    String convertMatnr(String matnr) throws Exception;
//
//    String convertLifnr(String lifnr) throws Exception;
//
//    JCoTable findAllDomvalue(String I_DOMNAME) throws Exception;
}
