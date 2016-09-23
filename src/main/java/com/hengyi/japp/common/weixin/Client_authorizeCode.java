package com.hengyi.japp.common.weixin;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import com.hengyi.japp.common.exception.WxException;
import com.hengyi.japp.common.weixin.dto.UnionInfoDTO;

import java.io.IOException;

import static com.hengyi.japp.common.Constant.MAPPER;

/**
 * Created by jzb on 16-5-4.
 */
public class Client_authorizeCode {
    private final long createInMillis;
    private final String appid;
    /**
     * originalNode 格式
     * 这里的access_token拥有较短的有效期，
     * 可以使用refresh_token进行刷新，refresh_token有效期为30天
     * 刷新后返回的数据格式一模一样
     * <p>
     * { "access_token":"ACCESS_TOKEN",
     * "expires_in":7200,
     * "refresh_token":"REFRESH_TOKEN",
     * "openid":"OPENID",
     * "scope":"SCOPE" 用户授权的作用域，使用逗号（,）分隔
     * "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL" 当且仅当该网站应用已获得该用户的userinfo授权时，才会出现该字段。
     * }
     */
    private final JsonNode originalNode;
    private boolean isFetch;
    private UnionInfoDTO unionInfo;

    public Client_authorizeCode(String appid, JsonNode originalNode) {
        createInMillis = System.currentTimeMillis();
        this.appid = appid;
        this.originalNode = originalNode;
    }

    public String getOpenid() {
        return originalNode.get("openid").asText();
    }

    /**
     * 当且仅当该网站应用已获得该用户的userinfo授权时，才会出现该字段。
     */
    public String getUnionid() {
        JsonNode node = originalNode.get("unionid");
        return node != null ? node.asText() : null;
    }

    public UnionInfoDTO getUnionInfo() throws IOException, WxException {
        return isFetch ? unionInfo : fetch();
    }

    private UnionInfoDTO fetch() throws IOException, WxException {
        isFetch = true;
//        if (StringUtils.contains(originalNode.get("scope").asText(), "snsapi_userinfo")) {
//        }
//        if (!Objects.equal("snsapi_userinfo", originalNode.get("scope").asText()))
//            throw new RuntimeException("scope <> snsapi_userinfo");
        String access_token = originalNode.get("access_token").asText();
        JsonNode node = null;
        try {
            node = WeixinClient.get(Urls.userinfo, ImmutableMap.of("access_token", access_token, "openid", getOpenid(), "lang", "zh_CN"));
        } catch (WxException ex) {
            //TODO 如果超时需要刷新 token，再取数  if(errcode==xxx)
            String refresh_token = node.get("refresh_token").asText();
            JsonNode refreshNode = WeixinClient.get(Urls.refresh_token, ImmutableMap.of("appid", appid, "grant_type", "refresh_token", "refresh_token", refresh_token));
            access_token = refreshNode.get("access_token").asText();
            node = WeixinClient.get(Urls.userinfo, ImmutableMap.of("access_token", access_token, "openid", getOpenid(), "lang", "zh_CN"));
        }
        unionInfo = node == null ? null : MAPPER.convertValue(node, UnionInfoDTO.class);
        return unionInfo;
    }

    class Urls {
        final static String access_token = "https://api.weixin.qq.com/sns/oauth2/access_token";
        final static String userinfo = "https://api.weixin.qq.com/sns/userinfo";
        final static String refresh_token = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    }
}
