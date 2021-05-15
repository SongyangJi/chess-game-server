package com.jsy.chessgameserver.websocket;


import com.jsy.chessgameserver.dto.ChatMessage;
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

public class ChatMessageDecoder implements Decoder.Text<ChatMessage> {


    @Override
    public ChatMessage decode(String s) throws DecodeException {
        ChatMessage chatMessage;
        try {
            chatMessage = JsonUtil.parse(s,ChatMessage.class);
        } catch (IOException e) {
            throw new DecodeException(s,"decode error");
        }
        return chatMessage;
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
