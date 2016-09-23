package com.hengyi.japp.common.weixin;

import com.google.common.collect.ImmutableMap;
import com.hengyi.japp.common.exception.WxException;
import com.hengyi.japp.common.weixin.msg.QyMsgSend;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import okhttp3.RequestBody;
import org.apache.commons.lang3.Validate;

import java.io.IOException;

/**
 * Created by jzb on 16-1-16.
 */
public class QyAgent extends WeixinClient {
    final QyClient qy;
    final WXBizMsgCrypt wxbmc;
    private final int agentid;

    QyAgent(QyClient qy, int agentid, String token, String encodingaeskey) throws AesException {
        Validate.notBlank(token);
        Validate.notBlank(encodingaeskey);
        this.qy = qy;
        this.agentid = agentid;
        wxbmc = new WXBizMsgCrypt(token, encodingaeskey, qy.corpid);
    }

    public void msg_send(QyMsgSend msg) throws IOException, WxException {
        RequestBody body = msg.requestBody();
        WeixinClient.post(Urls.msg_send, ImmutableMap.of("access_token", qy.access_token()), body);
    }

    public int getAgentid() {
        return agentid;
    }

    private class Urls {
        final static String msg_send = "https://qyapi.weixin.qq.com/cgi-bin/message/send";
    }
    /**
     * TODO 处理未关注
     * 如果无权限，则本次发送失败；如果收件人不存在或未关注，发送仍然执行。两种情况下均返回无效的部分
     * （注：由于userid不区分大小写，返回的列表都统一转为小写）。
     * {
     * "errcode": 0,
     * "errmsg": "ok",
     * "invaliduser": "UserID1",
     * "invalidparty":"PartyID1",
     * "invalidtag":"TagID1"
     * }
     */
}
