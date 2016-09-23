package com.hengyi.japp.common.weixin.msg;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hengyi.japp.common.Constant;
import com.hengyi.japp.common.weixin.QyAgent;

/**
 * Created by jzb on 16-5-4.
 */
public class QyMsgSend_text extends QyMsgSend {
    private final String content;

    public QyMsgSend_text(QyAgent agent, String content) {
        super(agent);
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
