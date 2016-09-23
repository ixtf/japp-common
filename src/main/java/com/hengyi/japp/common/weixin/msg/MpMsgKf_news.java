/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.common.weixin.msg;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hengyi.japp.common.weixin.MpClient;
import org.apache.commons.lang3.Validate;

import static com.hengyi.japp.common.Constant.MAPPER;

/**
 * @author jzb
 */
public class MpMsgKf_news extends MpMsgKf {
    private final ArrayNode articles = MAPPER.createArrayNode();

    public MpMsgKf_news(MpClient client, String touser) {
        super(client, touser);
    }

    public MpMsgKf_news addArticle(String title, String description, String url, String picurl) {
        if (articles.size() >= 10) return this;
        Validate.notBlank(title);
        ObjectNode node = MAPPER.createObjectNode();
        node.put("title", title);
        node.put("description", description);
        node.put("url", url);
        node.put("picurl", picurl);
        articles.add(node);
        return this;
    }

    public MpMsgKf_news addArticle(String title, String description, String url) {
        return addArticle(title, description, url, null);
    }

    @Override
    protected void requestBodyContent(ObjectNode body) {
        body.put("msgtype", "news");
        ObjectNode node = MAPPER.createObjectNode();
        node.set("articles", articles);
        body.set("news", node);
    }

}
