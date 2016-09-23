package com.hengyi.japp.common.sap;

public enum DestinationType {
    PRO("sap_pro.properties"), EQ("sap_eq.properties"), DEV("sap_dev.properties");

    private final String fileName;

    DestinationType(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
