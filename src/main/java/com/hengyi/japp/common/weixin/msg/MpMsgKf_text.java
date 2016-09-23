package com.hengyi.japp.common.weixin.msg;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hengyi.japp.common.Constant;
import com.hengyi.japp.common.weixin.MpClient;
import org.apache.commons.lang3.Validate;

/**
 * Created by jzb on 16-1-16.
 */
public class MpMsgKf_text extends MpMsgKf {
    private final String content;

    public MpMsgKf_text(MpClient client, String touser, String content) {
        super(client, touser);
        Validate.notBlank(content);
        this.content = content;
    }

    @Override
    protected void requestBodyContent(ObjectNode body) {
        body.put("msgtype", "text");
        ObjectNode node = Constant.MAPPER.createObjectNode();
        node.put("content", content);
        body.set("text", node);
    }
}