package com.jsy.chessgameserver.websocket;

import com.jsy.chessgameserver.util.JsonUtil;
import lombok.SneakyThrows;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

public class MessageEncoder implements Encoder.Text<Object> {
    @SneakyThrows
    @Override
    public String encode(Object object) {
        return JsonUtil.stringfy(object);
    }

    /**
     * Initialise the encoder.
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
