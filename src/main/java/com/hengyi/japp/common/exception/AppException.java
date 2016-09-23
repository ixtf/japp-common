package com.hengyi.japp.common.exception;

import java.util.Map;

/**
 * Created by jzb on 15-12-5.
 */
public class AppException extends Exception {
    private final String errorCode;
    private final Map<String, String> params;

    public AppException(String errorCode, Map<String, String> params) {
        this.errorCode = errorCode;
        this.params = params;
    }

    public AppException(String errorCode) {
        this(errorCode, null);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
