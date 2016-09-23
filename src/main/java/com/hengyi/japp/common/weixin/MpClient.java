package com.hengyi.japp.common.weixin;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import com.hengyi.japp.common.exception.WxException;
import com.hengyi.japp.common.weixin.dto.MpUnionInfoDTO;
import com.hengyi.japp.common.weixin.msg.MpMsgSend;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import okhttp3.RequestBody;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import static com.hengyi.japp.common.Constant.MAPPER;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by jzb on 16-1-15.
 */
public class MpClient extends WeixinClient {
    private final String appid;
    private final String secret;
    private final WXBizMsgCrypt wxbmc;
    private final MpClient_access_token access_token;

    MpClient(String appid, String secret, String token, String encodingaeskey) throws AesException {
        Validate.notBlank(appid);
        Validate.notBlank(secret);
        Validate.notBlank(token);
        Validate.notBlank(encodingaeskey);
        this.appid = appid;
        this.secret = secret;
        wxbmc = new WXBizMsgCrypt(token, encodingaeskey, appid);
        access_token = new MpClient_access_token();
    }

    public String access_token() throws IOException, WxException {
        return access_token.get();
    }

    /**
     * 微信网页授权,构造跳转链接
     */
    public String authorizeUrl(String redirect_uri, String state) {
        Validate.notBlank(redirect_uri);
        try {
            return MessageFormat.format(Urls.authorizeUrlTpl, appid,
                    URLEncoder.encode(redirect_uri, UTF_8.name()),
                    StringUtils.defaultString(state, "123"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 微信网页授权,链接跳转后的code处理
     */
    public Client_authorizeCode authorizeCode(String code, String state) throws IOException, WxException {
        JsonNode node = get(Client_authorizeCode.Urls.access_token, ImmutableMap.of("appid", appid, "secret", secret, "code", code, "grant_type", "authorization_code"));
        return new Client_authorizeCode(appid, node);
    }

    /**
     * 获取用户基本信息(UnionID机制)
     */
    public MpUnionInfoDTO userinfo(String openid, String state) throws IOException, WxException {
        JsonNode node = get(Urls.userinfo, ImmutableMap.of("access_token", access_token(), "secret", secret, "openid", openid));
        return MAPPER.convertValue(node, MpUnionInfoDTO.class);
    }

    public WXBizMsgCrypt getWxbmc() {
        return wxbmc;
    }

    public JsonNode msg_send(MpMsgSend msg) throws IOException, WxException {
        RequestBody body = msg.requestBody();
        return WeixinClient.post(msg.getSendUrl(), ImmutableMap.of("access_token", access_token()), body);
    }

    public static final class Urls {
        public static final String token = "https://api.weixin.qq.com/cgi-bin/token";
        public static final String authorizeUrlTpl = "https://open.weixin.qq.com/connect/oauth2/authorize?"
                + "appid={0}&"
                + "redirect_uri={1}&"
                + "response_type=code&"
                + "scope=snsapi_userinfo&"
                + "state={2}"
                + "#wechat_redirect";
        public static final String userinfo = "https://api.weixin.qq.com/cgi-bin/user/info";
        public static final String msgKf_send = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
        public static final String msgTpl_send = "https://api.weixin.qq.com/cgi-bin/message/template/send";
    }

    private class MpClient_access_token {
        private String access_token;
        private int expires_in;
        private long createInMillis;
        private long expiresInMillis;

        private String get() throws IOException, WxException {
            if (System.currentTimeMillis() > expiresInMillis)
                fetch();
            return access_token;
        }

        private synchronized void fetch() throws IOException, WxException {
            if (System.currentTimeMillis() < expiresInMillis)
                return;
            createInMillis = System.currentTimeMillis();
            JsonNode node = WeixinClient.get(Urls.token, ImmutableMap.of("grant_type", "client_credential", "appid", appid, "secret", secret));
            access_token = node.get("access_token").asText();
            expires_in = node.get("expires_in").asInt();
            expiresInMillis = createInMillis + TimeUnit.SECONDS.toMillis(expires_in - 30 * 60);
        }
    }
}
