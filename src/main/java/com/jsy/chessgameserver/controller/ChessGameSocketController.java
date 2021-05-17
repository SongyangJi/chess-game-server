package com.jsy.chessgameserver.controller;

import com.jsy.chessgameserver.dto.Message;
import com.jsy.chessgameserver.dto.endecoder.MessageDecoder;
import com.jsy.chessgameserver.dto.endecoder.MessageEncoder;
import com.jsy.chessgameserver.websocket.TopicEndpoint;
import org.springframework.stereotype.Component;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */
@ServerEndpoint(value = "/topic/{room}",
        decoders = {
                MessageDecoder.class},
        encoders = {
                MessageEncoder.class
        })
@Component
public class ChessGameSocketController extends TopicEndpoint {


    @OnMessage
    public void broker(Message message) {
        while (true) {
            broadcastMessage(message);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
