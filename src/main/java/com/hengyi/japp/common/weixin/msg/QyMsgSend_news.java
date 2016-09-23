package com.hengyi.japp.common.weixin.msg;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hengyi.japp.common.weixin.QyAgent;
import org.apache.commons.lang3.Validate;

import static com.hengyi.japp.common.Constant.MAPPER;

/**
 * Created by jzb on 16-5-4.
 */
public class QyMsgSend_news extends QyMsgSend {
    /**
     * 图文消息，一个图文消息支持1到8条图文
     */
    private final ArrayNode articles = MAPPER.createArrayNode();

    public QyMsgSend_news(QyAgent agent) {
        super(agent);
    }

    public QyMsgSend_news addArticle(String title, String description, String url, String picurl) {
        if (articles.size() >= 8) return this;
        Validate.notBlank(title);
        ObjectNode node = MAPPER.createObjectNode();
        node.put("title", title);
        node.put("description", description);
        node.put("url", url);
        node.put("picurl", picurl);
        articles.add(node);
        return this;
    }

    public QyMsgSend_news addArticle(String title, String description, String url) {
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
