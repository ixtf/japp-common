package com.hengyi.japp.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.Validate;

import java.io.IOException;

import static com.hengyi.japp.common.Constant.MAPPER;

/**
 * Created by jzb on 15-12-5.
 */
public class J_json {
    static String writeValueAsString(Object o) {
        Validate.notNull(o);
        try {
            return MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    static <T> T readValue(String o, Class<T> clazz) {
        Validate.notNull(o);
        try {
            return MAPPER.readValue(o, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static <T> T readValue(JsonParser o, Class<T> clazz) {
        Validate.notNull(o);
        try {
            return MAPPER.readValue(o, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
