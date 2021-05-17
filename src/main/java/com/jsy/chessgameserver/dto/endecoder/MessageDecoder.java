package com.jsy.chessgameserver.dto.endecoder;


import com.jsy.chessgameserver.dto.ChatMessage;
import com.jsy.chessgameserver.dto.Message;
import com.jsy.chessgameserver.util.JsonUtil;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

public class MessageDecoder implements Decoder.Text<Message> {


    @Override
    public Message decode(String s) throws DecodeException {
        Message message;
        try {
            message = JsonUtil.parse(s,Message.class);
        } catch (IOException e) {
            throw new DecodeException(s,"decode error");
        }
        return message;
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
