package com.hengyi.japp.common.weixin;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.hengyi.japp.common.exception.WxException;
import com.hengyi.japp.common.weixin.dto.MpUnionInfoDTO;
import com.hengyi.japp.common.weixin.msg.MpMsgSend;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import okhttp3.RequestBody;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.hengyi.japp.common.Constant.MAPPER;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by jzb on 16-1-15.
 */
public class MpClient extends WeixinClient {
    private final String appid;
    private final String token;
    private final String secret;
    private final WXBizMsgCrypt wxbmc;
    private final MpClient_access_token access_token;
    private final MpClient_jsapi_ticket jsapi_ticket;

    MpClient(String appid, String secret, String token, String encodingaeskey) throws AesException {
        Validate.notBlank(appid);
        Validate.notBlank(secret);
        Validate.notBlank(token);
        Validate.notBlank(encodingaeskey);
        this.appid = appid;
        this.secret = secret;
        this.token = token;
        wxbmc = new WXBizMsgCrypt(token, encodingaeskey, appid);
        access_token = new MpClient_access_token();
        jsapi_ticket = new MpClient_jsapi_ticket();
    }

    public String access_token() throws IOException, WxException {
        return access_token.get();
    }

    public String jsapi_ticket() throws IOException, WxException {
        return jsapi_ticket.get();
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
    public MpUnionInfoDTO userinfo(String openid) throws IOException, WxException {
        JsonNode node = get(Urls.userinfo, ImmutableMap.of("access_token", access_token(), "secret", secret, "openid", openid));
        return MAPPER.convertValue(node, MpUnionInfoDTO.class);
    }

    public String verifyUrl(String msgSignature, String timeStamp, String nonce, String echoStr) throws AesException {
        //TODO:服务号和企业号的验证有所不同，需修改
        String s = getSHA1(timeStamp, nonce);
        if (s.equals(msgSignature))
            return echoStr;
        throw new RuntimeException();
    }

    public Map<String, String> jsConfig(String url) throws AesException, IOException, WxException, NoSuchAlgorithmException {
        Map<String, String> map = Maps.newHashMap();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String nonceStr = RandomStringUtils.randomAlphanumeric(6);

        String string1 = "jsapi_ticket=" + jsapi_ticket() + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + url;
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(string1.getBytes("UTF-8"));
        String signature = byteToHex(crypt.digest());

        map.put("appId", appid);
        map.put("timestamp", timestamp);
        map.put("nonceStr", nonceStr);
        map.put("signature", signature);
        return map;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public String decryptMsg(String msgSignature, String timeStamp, String nonce, String postData) throws AesException {
        return wxbmc.decryptMsg(msgSignature, timeStamp, nonce, postData);
    }

    public JsonNode msg_send(MpMsgSend msg) throws IOException, WxException {
        RequestBody body = msg.requestBody();
        return WeixinClient.post(msg.getSendUrl(), ImmutableMap.of("access_token", access_token()), body);
    }

    /*
    * @param map {
    *  expire_seconds  2592000
    *  action_name QR_SCENE
    *  action_info {
    *       scene{
    *       scene_id: 123
    *       }
    *   }
    * }
    * */
    public String qrcodeTicket(JsonNode postNode) throws IOException, WxException {
        RequestBody body = RequestBody.create(WeixinClient.JSON, MAPPER.writeValueAsString(postNode));
        JsonNode node = WeixinClient.post(Urls.qrcode, ImmutableMap.of("access_token", access_token()), body);
        return node.get("ticket").asText();
    }

    public static final class Urls {
        public static final String qrcode = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
        public static final String ticket = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
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

    private class MpClient_jsapi_ticket {
        private String ticket;
        private int expires_in;
        private long createInMillis;
        private long expiresInMillis;

        private String get() throws IOException, WxException {
            if (System.currentTimeMillis() > expiresInMillis)
                fetch();
            return ticket;
        }

        private synchronized void fetch() throws IOException, WxException {
            if (System.currentTimeMillis() < expiresInMillis)
                return;
            createInMillis = System.currentTimeMillis();
            JsonNode node = WeixinClient.get(Urls.ticket, ImmutableMap.of("type", "jsapi", "access_token", access_token()));
            ticket = node.get("ticket").asText();
            expires_in = node.get("expires_in").asInt();
            expiresInMillis = createInMillis + TimeUnit.SECONDS.toMillis(expires_in - 30 * 60);
        }
    }

    private String getSHA1(String timestamp, String nonce) throws AesException {
        try {
            String[] array = new String[]{token, timestamp, nonce};
            StringBuffer sb = new StringBuffer();
            // 字符串排序
            Arrays.sort(array);
            for (int i = 0; i < array.length; i++) {
                sb.append(array[i]);
            }
            String str = sb.toString();
            // SHA1签名生成
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
