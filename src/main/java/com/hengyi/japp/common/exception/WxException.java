package com.hengyi.japp.common.exception;

/**
 * Created by jzb on 16-1-16.
 */
public class WxException extends AppException {
    private final String errmsg;

    public WxException(int errcode, String errmsg) {
        super("WXE_" + errcode);
        this.errmsg = errmsg;
    }

    public String getErrmsg() {
        return errmsg;
    }
}
