package com.jsy.chessgameserver.websocket;

import com.jsy.chessgameserver.dto.ChatMessage;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description: 聊天 socket服务端
 */


@Slf4j
@ServerEndpoint(value = "/topic/{room}",
        decoders = {
                ChatMessageDecoder.class},
        encoders = {
                ChatMessageEncoder.class
        })
@Component
public class TopicEndpoint {

    // 该会话所属的房间
    private Room room = null;

    private String roomId = null;

    // 属于该会话的收发消息的通道
    private Channel channel;


    private static final Map<String, Room> rooms = new ConcurrentHashMap<>();

    @OnOpen
    public void open(@PathParam("room") @NonNull String roomId, Session session) {
        log.info("TopicEndpoint bean is {}", this);
        synchronized (rooms) {
            if (!rooms.containsKey(roomId)) {
                room = RoomPool.acquireRoom(roomId);
                if (room != null) {
                    rooms.put(roomId, room);
                    this.roomId = roomId;
                }
            } else {
                room = rooms.get(roomId);
            }
        }

        /*
         *  可以在 controller 进行控制，但是为了程序的健壮，防止客户端绕过控制层,
         *  这里仍然要进行拦截
         */
        if (room == null) {
            log.warn("房间数已满, 请求创建房间 {} 被拒绝", roomId);
            try {
                session.getBasicRemote().sendText("error,not permit creating a new room");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        channel = new Channel(session);
        room.addChannel(channel);
        log.info("websocket session {} open, in room {}", session, room);
    }

//    @OnMessage
//    public void broker(String textMessage) {
//        textMessage = channel.getId() + "说： " + textMessage;
//        try {
//            room.broadcast(textMessage);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @SneakyThrows
    @OnMessage
    public void broker(ChatMessage chatMessage) {
        room.broadcast(chatMessage);
    }


    @SneakyThrows
    @OnError
    public void error(Session session, Throwable throwable) {
        log.error("session:{} in room:{}_{}", session.getId(), this
                .roomId, throwable.getMessage(), throwable);
        session.close();
        close( new CloseReason(null,"socket error"));
    }


    @OnClose
    public void close(CloseReason reason) {
        // 房间已销毁
        if (room.removeChannel(channel)) {
            synchronized (rooms) {
                // 此时 room 已经被清空状态了
                rooms.remove(roomId);
                log.info("房间 {} 已销毁", roomId);
            }
        }
        log.info("Closing a webSocket session {} due to {}", channel.getSession(), reason.getReasonPhrase());
    }
}
