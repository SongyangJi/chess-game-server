package com.jsy.chessgameserver.websocket;

import com.jsy.chessgameserver.dto.ChatMessage;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
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

    private static final ReentrantReadWriteLock lock;

    private static final Lock readLock;
    private static final Lock writeLock;


    static {
        channels = new ConcurrentHashMap<>();
        lock = new ReentrantReadWriteLock();
        readLock = lock.readLock();
        writeLock = lock.writeLock();
    }

    @OnOpen
    public void open(@PathParam("routingKey") @NonNull String routingKey, Session session) {
        log.info("socket {} create session{}_{}", routingKey, session.getId(), session);
        this.routingKey = routingKey;
        writeLock.lock();
        try {
            channels.put(routingKey, new Channel(session));
        } finally {
            writeLock.unlock();
        }
    }

    @SneakyThrows
    @OnMessage
    public void message(ChatMessage chatMessage) {
        readLock.lock();
        try {
            if (channels.containsKey(chatMessage.getTo())) {
                channels.get(chatMessage.getTo()).sendObject(chatMessage);
            }
        } finally {
            readLock.unlock();
        }

    }

    private void destroy() {
        writeLock.lock();
        try {
            channels.remove(this.routingKey);
        } finally {
            writeLock.unlock();
        }
    }


    @OnClose
    public void close(Session session, CloseReason reason) {
        destroy();
        log.info("Closing a webSocket session {} due to {}", session, reason.getReasonPhrase());
    }

    @SneakyThrows
    @OnError
    public void error(Session session, Throwable throwable) {
        session.close();
        destroy();
        log.error("session:{}_{}", session.getId(), throwable.getMessage(), throwable);
    }

}
