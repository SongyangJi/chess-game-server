package com.jsy.chessgameserver.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jsy.chessgameserver.dto.Message;
import com.jsy.chessgameserver.util.JsonUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import javax.websocket.server.PathParam;
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
public abstract class TopicEndpoint {

    // 该会话所属的房间
    protected Room room = null;

    protected String roomId = null;

    // 属于该会话的收发消息的通道
    protected Channel channel;


    protected static final Map<String, Room> rooms = new ConcurrentHashMap<>();

    @OnOpen
    public void open(@PathParam("roomId") @NonNull String roomId, Session session) {
        log.info("TopicEndpoint bean is {}", this);
        this.roomId = roomId;
        if (!rooms.containsKey(roomId)) {
            room = RoomPool.acquireRoom(roomId);
            if (room != null) {
                rooms.put(roomId, room);

            }
        } else {
            room = rooms.get(roomId);
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
        log.info("websocket session {} open, in room {};    Endpoint bean is {}", session, room, this);
    }


    @OnError
    public void error(Session session, Throwable throwable) {
        channel.destroy();
        // 出错就要关闭
        close(new CloseReason(null, "socket error"));
        log.error("session:{} in room:{}_{}", session.getId(), this
                .roomId, throwable.getMessage(), throwable);
    }


    @OnClose
    public void close(CloseReason reason) {
        // 房间已销毁
        if (room.removeChannel(channel)) {
            // 此时 room 已经被清空状态了
            rooms.remove(roomId);
            log.info("房间 {} 被销毁", roomId);
        }
        log.info("Closing a webSocket session {} due to {}", channel.getSession(), reason.getReasonPhrase());
    }


    protected void broadcastMessage(Message message) {
        room.broadcast(message);
    }

    public static void broadcastMessage(String roomId, Message message) throws JsonProcessingException {
        Room room = rooms.get(roomId);
        if (room != null) {
            room.broadcast(JsonUtil.stringfy(message));
        }
    }

}
