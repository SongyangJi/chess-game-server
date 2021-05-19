package com.jsy.chessgameserver.dto.endecoder;

import com.jsy.chessgameserver.dto.chess.GameMessage;
import com.jsy.chessgameserver.util.JsonUtil;
import lombok.SneakyThrows;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

public class GameMessageEncoder implements Encoder.Text<GameMessage> {

    @SneakyThrows
    @Override
    public String encode(GameMessage object) throws EncodeException {
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
