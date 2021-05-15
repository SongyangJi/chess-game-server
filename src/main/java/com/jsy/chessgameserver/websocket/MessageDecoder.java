package com.jsy.chessgameserver.websocket;


import com.jsy.chessgameserver.util.JsonUtil;
import lombok.SneakyThrows;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

public class MessageDecoder implements Decoder.Text<Object> {
    @SneakyThrows
    @Override
    public Object decode(String s) {
        return JsonUtil.parse(s, Object.class);
    }

    @Override
    public boolean willDecode(String s) {
        return s != null && s.length() > 0;
    }

    /**
     * Initialise the decoder.
     *
     * @param endpointConfig The end-point configuration
     */
    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    /**
     * Destroy the decoder.
     */
    @Override
    public void destroy() {

    }
}
