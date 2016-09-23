package com.hengyi.japp.common.weixin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Maps;
import com.hengyi.japp.common.exception.WxException;
import com.qq.weixin.mp.aes.AesException;
import okhttp3.*;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static com.hengyi.japp.common.Constant.MAPPER;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by jzb on 16-1-16.
 */
public abstract class WeixinClient {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final static Map<String, WeixinClient> clients = Maps.newConcurrentMap();
    private final static OkHttpClient httpClient = new OkHttpClient();

    public synchronized static <T> T instance(Class<? extends WeixinClient> clazz, Properties p) throws AesException {
        WeixinClient result = null;
        if (clazz == QyAgent.class) {
            QyClient qy = instance(QyClient.class, p);
            return (T) qy.agent(p);
        } else if (clazz == QyClient.class) {
            String corpId = p.getProperty("corpid");
            result = clients.get(corpId);
            if (result == null) {
                result = new QyClient(corpId, p.getProperty("corpsecret"));
                clients.put(corpId, result);
            }
        } else if (clazz == MpClient.class) {
            String appid = p.getProperty("appid");
            result = clients.get(appid);
            if (result == null) {
                result = new MpClient(appid, p.getProperty("secret"), p.getProperty("token"), p.getProperty("encodingaeskey"));
                clients.put(appid, result);
            }
        } else if (clazz == OpenClient.class) {
            String appid = p.getProperty("appid");
            result = clients.get(appid);
            if (result == null) {
                result = new OpenClient(appid, p.getProperty("secret"));
                clients.put(appid, result);
            }
        } else
            throw new RuntimeException("clazz[" + clazz.getName() + "],not a client!");
        return (T) result;
    }

    final static JsonNode get(String url, Map<String, String> queryMap) throws IOException, WxException {
        Request req = buildUrl(url, queryMap).build();
        Response res = httpClient.newCall(req).execute();
        return checkErrorAndGetNode(res);
    }

    final static JsonNode post(String url, Map<String, String> queryMap, RequestBody body) throws IOException, WxException {
        Request req = buildUrl(url, queryMap).post(body).build();
        Response res = httpClient.newCall(req).execute();
        return checkErrorAndGetNode(res);
    }

    private static JsonNode checkErrorAndGetNode(Response res) throws WxException, IOException {
        if (!res.isSuccessful())
            throw new RuntimeException();
        ObjectNode result = MAPPER.readValue(res.body().byteStream(), ObjectNode.class);
        JsonNode errcode = result.get("errcode");
        if (errcode != null && errcode.asInt() > 0)
            throw new WxException(errcode.asInt(), result.get("errmsg").asText());
        return result;
    }

    private static Request.Builder buildUrl(String url, Map<String, String> queryMap) {
        Validate.notBlank(url);
        Request.Builder result = new Request.Builder();
        if (MapUtils.isEmpty(queryMap))
            return result.url(url);
        String queryS = queryMap.entrySet().stream()
                .filter(entry -> StringUtils.isNotBlank(entry.getValue()))
                .map(entry -> {
                    try {
                        return entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), UTF_8.name());
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException();
                    }
                })
                .collect(Collectors.joining("&"));
        String joinS = (url.indexOf("?") == -1) ? "?" : "&";
        return result.url(url + joinS + queryS);
    }
}
