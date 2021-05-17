package com.jsy.chessgameserver.websocket;

import com.jsy.chessgameserver.dto.Message;
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
 * @Description:
 */

//@ServerEndpoint(value = "/queue/{routingKey}",
//        decoders = {
//                MessageDecoder.class},
//        encoders = {
//                MessageEncoder.class
//        })
//@Component
@Slf4j
public abstract class QueueEndpoint {

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


    protected void send(Message message) {
        String toKey = message.getTo();
        try {
            Channel channel = channels.get(toKey);
            if (channel != null) {
                channel.sendObject(message);
            }
        } catch (IOException | EncodeException e) {
            log.error("ChatMessage {} 发送失败_{}", message, e.getMessage(), e);
            destroy();
        }
    }

}
