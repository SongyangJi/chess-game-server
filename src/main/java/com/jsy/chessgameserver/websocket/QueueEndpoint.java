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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

@Slf4j
@ServerEndpoint(value = "/queue/{routingKey}",
        decoders = {
                ChatMessageDecoder.class},
        encoders = {
                ChatMessageEncoder.class
        })
@Component
public class QueueEndpoint {

    private String routingKey;

    private static final Map<String, Channel> channels;


    static {
        channels = new ConcurrentHashMap<>();
    }

    @OnOpen
    public void open(@PathParam("routingKey") @NonNull String routingKey, Session session) {
        this.routingKey = routingKey;
        channels.put(routingKey, new Channel(session));
        log.info("socket {} create session{}_{}", routingKey, session.getId(), session);
    }

    /**
     * ps:
     *
     * @param chatMessage 聊天消息体
     */
    @OnMessage
    public void message(ChatMessage chatMessage,Session session) {
        String toKey = chatMessage.getTo();
        try {
            Channel channel = channels.get(toKey);
            if (channel != null) {
                channel.sendObject(chatMessage);
            }
        } catch (IOException | EncodeException e) {
            log.error("ChatMessage {} 发送失败_{}",chatMessage, e.getMessage(), e);
            destroy(session);
        }
    }

    private void destroy(Session session) {
        // 出错则关闭 session,
        try {
            if(session.isOpen()) {
                session.close();
            }
        } catch (IOException e) {
            log.error("session 关闭异常 {} session_id:{}_{}",session,session.getId(),e.getMessage(),e);
        } finally {
            channels.remove(this.routingKey);
        }
    }


    @OnClose
    public void close(Session session, CloseReason reason) {
        destroy(session);
        log.info("Closing a webSocket session {} due to {}", session, reason.getReasonPhrase());
    }

    @OnError
    public void error(Session session, Throwable throwable) {
        destroy(session);
        log.error("session:{}_{}", session.getId(), throwable.getMessage(), throwable);
    }

}
