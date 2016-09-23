package com.hengyi.japp.common.weixin.msg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.hengyi.japp.common.exception.WxException;
import com.hengyi.japp.common.weixin.MpClient;
import okhttp3.RequestBody;
import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by jzb on 16-1-16.
 */
public abstract class MpMsgSend implements Serializable {
    protected final MpClient mp;
    protected final String touser;

    public MpMsgSend(MpClient mp, String touser) {
        Validate.notBlank(touser);
        this.mp = mp;
        this.touser = touser;
    }

    public abstract String getSendUrl();

    public abstract RequestBody requestBody() throws JsonProcessingException;

    public final JsonNode send() throws IOException, WxException {
        return mp.msg_send(this);
    }
}
