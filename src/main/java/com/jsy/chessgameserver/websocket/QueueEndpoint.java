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

    Channel channel;

    static {
        channels = new ConcurrentHashMap<>();
    }

    @OnOpen
    public void open(@PathParam("routingKey") @NonNull String routingKey, Session session) {
        this.routingKey = routingKey;
        channel = new Channel(session);
        channels.put(routingKey, channel);
        log.info("socket {} create session{}_{}", routingKey, session.getId(), session);
    }

    /**
     * ps:
     *
     * @param chatMessage 聊天消息体
     */
    @OnMessage
    public void message(ChatMessage chatMessage, Session session) {
        String toKey = chatMessage.getTo();
        try {
            Channel channel = channels.get(toKey);
            if (channel != null) {
                channel.sendObject(chatMessage);
            }
        } catch (IOException | EncodeException e) {
            log.error("ChatMessage {} 发送失败_{}", chatMessage, e.getMessage(), e);
            destroy();
        }
    }

    private void destroy() {
        channel.destroy();
        channels.remove(this.routingKey);
    }


    @OnClose
    public void close(Session session, CloseReason reason) {
        destroy();
        log.info("Closing a webSocket session {} due to {}", session, reason.getReasonPhrase());
    }

    @OnError
    public void error(Session session, Throwable throwable) {
        destroy();
        log.error("session:{}_{}", session.getId(), throwable.getMessage(), throwable);
    }

}
