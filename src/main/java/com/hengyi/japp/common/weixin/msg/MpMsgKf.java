/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.common.weixin.msg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hengyi.japp.common.weixin.MpClient;
import com.hengyi.japp.common.weixin.WeixinClient;
import okhttp3.RequestBody;

import static com.hengyi.japp.common.Constant.MAPPER;

/**
 * @author jzb
 */
public abstract class MpMsgKf extends MpMsgSend {
    public MpMsgKf(MpClient mp, String touser) {
        super(mp, touser);
    }

    @Override
    public String getSendUrl() {
        return MpClient.Urls.msgKf_send;
    }

    @Override
    public final RequestBody requestBody() throws JsonProcessingException {
        ObjectNode node = MAPPER.createObjectNode();
        node.put("touser", touser);
        requestBodyContent(node);
        return RequestBody.create(WeixinClient.JSON, MAPPER.writeValueAsString(node));
    }

    protected abstract void requestBodyContent(ObjectNode body);
}