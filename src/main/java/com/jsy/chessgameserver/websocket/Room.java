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

    private final Map<Channel, Boolean> channels;

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
     */
    void broadcast(String text) {
        for (Channel channel : channels.keySet()) {
            try {
                channel.sendText(text);
            } catch (IOException e) {
                // 通道销毁
                channel.destroy();
                // 谁出错，移除谁，但是其他人不要影响
                removeChannel(channel);
                log.warn("消息 {} 发送失败", text);
                log.error("Channel {} 出错_{}", channel, e.getMessage(), e);
            }
        }
    }

    /**
     * 同一个"房间"间消息发送方式 —— 广播消息
     *
     * @param t 泛型对象
     */

    <T> void broadcast(T t) {
        for (Channel channel : channels.keySet()) {
            try {
                channel.sendObject(t);
            } catch (IOException | EncodeException e) {
                // 通道销毁
                channel.destroy();
                // 谁出错，移除谁，但是其他人不要影响
                removeChannel(channel);
                log.warn("消息 {} 发送失败", t);
                log.error("Channel {} 出错_{}", channel, e.getMessage(), e);
            }
        }
    }

    void addChannel(Channel channel) {
        channels.put(channel, true);
    }

    boolean removeChannel(Channel channel) {
        channels.remove(channel);
        log.info("移除会话通道 {}", channel.getId());
        // 如果是空房间，则销毁房间，并返还给对象池
        return destroy();
    }



    boolean destroy() {
        if (channels.isEmpty()) {
            log.info("房间 {} 清空", id);
            // 清空后返回对象池
            initializeState();
            RoomPool.releaseRoom(this);
            return true;
        }
        return false;
    }

    void initializeState() {
        id = null;
        channels.clear();
    }

    int getRoomSize() {
        return channels.size();
    }

}
