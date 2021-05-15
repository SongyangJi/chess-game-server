package com.jsy.chessgameserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description: Json 与 Java 互转的工具类
 */

public final class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Serialize any Java value as a String.
     */
    public static String stringfy(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    /**
     * Deserialize JSON content from given JSON content String.
     */
    public static <T> T parse(String content, Class<T> valueType) throws IOException {
        return mapper.readValue(content, valueType);
    }


}