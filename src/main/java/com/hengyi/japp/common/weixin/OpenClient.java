package com.hengyi.japp.common.weixin;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import com.hengyi.japp.common.exception.WxException;
import org.apache.commons.lang3.Validate;

import java.io.IOException;

/**
 * Created by jzb on 16-1-15.
 */
public class OpenClient extends WeixinClient {
    private final String appid;
    private final String secret;

    OpenClient(String appid, String secret) {
        Validate.notBlank(appid);
        Validate.notBlank(secret);
        this.appid = appid;
        this.secret = secret;
    }

    public Client_authorizeCode authorizeCode(String code) throws IOException, WxException {
        JsonNode node = get(Client_authorizeCode.Urls.access_token, ImmutableMap.of("appid", appid, "secret", secret, "code", code, "grant_type", "authorization_code"));
        return new Client_authorizeCode(appid, node);
    }
}
