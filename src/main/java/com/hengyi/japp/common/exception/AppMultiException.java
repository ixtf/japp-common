package com.hengyi.japp.common.exception;

import java.util.List;

public class AppMultiException extends Exception {

    private final List<AppException> appExceptions;

    public AppMultiException(List<AppException> appExceptions) {
        this.appExceptions = appExceptions;
    }

    public List<AppException> getAppExceptions() {
        return appExceptions;
    }
}
