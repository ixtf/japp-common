package com.hengyi.japp.common.weixin.msg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import com.hengyi.japp.common.exception.WxException;
import com.hengyi.japp.common.weixin.QyAgent;
import com.hengyi.japp.common.weixin.WeixinClient;
import okhttp3.RequestBody;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.Set;

import static com.hengyi.japp.common.Constant.MAPPER;

/**
 * Created by jzb on 16-5-4.
 */
public abstract class QyMsgSend {
    private final QyAgent agent;
    private boolean all;
    /**
     * 成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送
     */
    private Set<String> touser;
    /**
     * 部门ID列表，多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数
     */
    private Set<String> toparty;
    /**
     * 标签ID列表，多个接收者用‘|’分隔。当touser为@all时忽略本参数
     */
    private Set<String> totag;
    /**
     * 表示是否是保密消息，0表示否，1表示是，默认0
     */
    private String safe;

    public QyMsgSend(QyAgent agent) {
        this.agent = agent;
    }

    public QyMsgSend all(boolean all) {
        this.all = all;
        return this;
    }

    public QyMsgSend safe(String safe) {
        this.safe = Objects.equal("1", safe) ? "1" : null;
        return this;
    }

    public QyMsgSend addTouser(String s) {
        if (touser == null)
            touser = Sets.newHashSet();
        touser.add(s);
        return this;
    }

    public QyMsgSend addToparty(String s) {
        if (toparty == null)
            toparty = Sets.newHashSet();
        toparty.add(s);
        return this;
    }

    public QyMsgSend addTotag(String s) {
        if (totag == null)
            totag = Sets.newHashSet();
        totag.add(s);
        return this;
    }

    public void send() throws IOException, WxException {
        agent.msg_send(this);
    }

    public RequestBody requestBody() throws JsonProcessingException {
        ObjectNode node = MAPPER.createObjectNode();
        node.put("agentid", agent.getAgentid());
        node.put("safe", safe);
        if (all) {
            node.put("touser", "@all");
        } else {
            if (CollectionUtils.isNotEmpty(touser))
                node.put("touser", String.join("|", touser));
            if (CollectionUtils.isNotEmpty(toparty))
                node.put("toparty", String.join("|", toparty));
            if (CollectionUtils.isNotEmpty(totag))
                node.put("totag", String.join("|", totag));
        }
        requestBodyContent(node);
        return RequestBody.create(WeixinClient.JSON, MAPPER.writeValueAsString(node));
    }

    protected abstract void requestBodyContent(ObjectNode body);
}
