package com.hengyi.japp.common.weixin.msg;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hengyi.japp.common.weixin.MpClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import static com.hengyi.japp.common.Constant.MAPPER;

/**
 * Created by jzb on 16-5-4.
 */
public class MpMsgTpl extends MpMsgKf {
    private final String template_id;
    private final String url;
    private final ObjectNode data = MAPPER.createObjectNode();

    public MpMsgTpl(MpClient mp, String touser, String template_id, String url) {
        super(mp, touser);
        Validate.notBlank(template_id);
        this.template_id = template_id;
        this.url = url;
    }

    public MpMsgTpl data(String key, String value, String color) {
        ObjectNode node = MAPPER.createObjectNode();
        node.put("value", value);
        node.put("color", StringUtils.defaultString(color, "#173177"));
        data.set(key, node);
        return this;
    }

    public MpMsgTpl data(String key, String value) {
        return data(key, value, null);
    }

    @Override
    protected void requestBodyContent(ObjectNode body) {
        body.put("template_id", template_id);
        body.put("url", url);
        body.set("data", data);
    }

    @Override
    public String getSendUrl() {
        return MpClient.Urls.msgTpl_send;
    }
}
