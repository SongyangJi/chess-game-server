package com.jsy.chessgameserver.websocket;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

@Slf4j
@Getter
public class Room {

    @Setter
    private String id;

    private Map<Channel, Boolean> channels;

    public Room() {
        channels = new ConcurrentHashMap<>();
    }

    protected Room(String id) {
        this.id = id;
        channels = new ConcurrentHashMap<>();
    }

    /**
     * 同一个"房间"间消息发送方式 —— 广播消息
     *
     * @param text 字符串消息
     * @throws IOException IO异常
     */
    void broadcast(String text) throws IOException {
        for (Channel channel : channels.keySet()) {
            channel.sendText(text);
        }
    }

    /**
     * 同一个"房间"间消息发送方式 —— 广播消息
     *
     * @param t 泛型对象
     * @throws IOException IO异常
     */
    <T> void broadcast(T t) throws IOException, EncodeException {
        for (Channel channel : channels.keySet()) {
            channel.sendObject(t);
        }
    }

    void addChannel(Channel channel) {
        channels.put(channel,true);
    }

    boolean removeChannel(Channel channel) {
        log.info("移除会话通道 {}", channel.getId());
        channels.remove(channel);
        // 如果是空房间，则销毁房间，并返还给对象池
        return destroy();
    }

//    void removeChannelById(String id) {
//        channels.remove(id);
//    }


    boolean destroy() {
        if (channels.isEmpty()) {
            log.info("房间 {} 清空", id);
            // 清空后返回对象池
            RoomPool.releaseRoom(this);
            return true;
        }
        return false;
    }

    void initializeState() {
        id = null;
        channels.clear();
    }

}
