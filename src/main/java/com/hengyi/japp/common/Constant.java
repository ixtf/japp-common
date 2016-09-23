package com.hengyi.japp.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Created by jzb on 15-12-5.
 */
public class Constant {
    public static final String ADMIN = "admin";
    public static final ObjectMapper MAPPER = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .enable(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)
            .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    /**
     * jwt token 永久的加密key
     * UUID.nameUUIDFromBytes("SHARE_JWT_KEY_JAPP".getBytes(StandardCharsets.UTF_8));
     */
    public static final String SHARE_JWT_KEY = "8c269208-5874-33d2-93eb-443f9930d7e2";
    /**
     * jwt token 永久的加密key
     * UUID.nameUUIDFromBytes("PERMANENT_JWT_KEY_JAPP".getBytes(StandardCharsets.UTF_8));
     */
    public static final String PERMANENT_JWT_KEY = "3bdbb00a-3806-3d20-9fad-cc75b58f4464";

    public static void main(String[] args) {
        UUID uuid = UUID.nameUUIDFromBytes("SHARE_JWT_KEY_JAPP".getBytes(StandardCharsets.UTF_8));
        System.out.println(uuid);
        uuid = UUID.nameUUIDFromBytes("PERMANENT_JWT_KEY_JAPP".getBytes(StandardCharsets.UTF_8));
        System.out.println(uuid);
    }
}
