package com.jsy.chessgameserver.controller;

import com.jsy.chessgameserver.dto.ChatMessage;
import com.jsy.chessgameserver.dto.endecoder.ChatMessageDecoder;
import com.jsy.chessgameserver.dto.endecoder.ChatMessageEncoder;
import com.jsy.chessgameserver.websocket.TopicEndpoint;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

@Slf4j
@ServerEndpoint(value = "/topic/chat/{roomId}",
        decoders = {
                ChatMessageDecoder.class},
        encoders = {
                ChatMessageEncoder.class
        })
@Controller
public class ChatSocketController extends TopicEndpoint {

    @OnOpen
    @Override
    public void open(@PathParam("roomId") @NonNull String roomId, Session session) {
        super.open(roomId, session);
    }

    /**
     * 因为是群聊，受到消息就广播
     *
     * @param chatMessage
     */
    @OnMessage
    public void broker(ChatMessage chatMessage) {
//        System.out.println("受到信息" + chatMessage);
//        System.out.println("房间ID  "+super.roomId);
        chatMessage.setTime(new Date());
//        chatMessage.setTo(super.roomId);
        broadcastMessage(chatMessage);
    }
}
