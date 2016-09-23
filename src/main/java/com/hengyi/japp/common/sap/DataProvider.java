package com.hengyi.japp.common.sap;

import com.google.common.collect.ImmutableMap;
import com.sap.conn.jco.ext.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class DataProvider implements DestinationDataProvider, ServerDataProvider {
    private final Map<String, Properties> secureDBStorage;

    DataProvider() {
        ImmutableMap.Builder<String, Properties> builder = ImmutableMap.builder();
        for (DestinationType type : DestinationType.values()) {
            builder.put(type.name(), getProperties(type.getFileName()));
        }
        secureDBStorage = builder.build();
    }

    private Properties getProperties(String path) {
        try {
            InputStream in = DataProvider.class.getResourceAsStream(path);
            Properties p = new Properties();
            p.load(in);
            return p;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Properties getDestinationProperties(String destinationName) {
        try {
            Properties p = secureDBStorage.get(destinationName);
            if (p == null || p.isEmpty()) {
                throw new DataProviderException(DataProviderException.Reason.INVALID_CONFIGURATION, "destination configuration is incorrect", null);
            }
            return p;
        } catch (RuntimeException re) {
            throw new DataProviderException(DataProviderException.Reason.INTERNAL_ERROR, re);
        }
    }

    @Override
    public void setDestinationDataEventListener(DestinationDataEventListener eL) {
    }

    @Override
    public boolean supportsEvents() {
        return false;
    }

    @Override
    public Properties getServerProperties(String serverName) {
        return getDestinationProperties(serverName);
    }

    @Override
    public void setServerDataEventListener(ServerDataEventListener eL) {
    }

}
