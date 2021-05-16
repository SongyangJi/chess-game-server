package com.jsy.chessgameserver.websocket;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

public class RoomPool {

    static final int DEFAULT_SIZE = 100;

    static int count = 0;

    static final ConcurrentLinkedQueue<Room> pool;

    static {
        pool = new ConcurrentLinkedQueue<>();
    }

    synchronized static void preStart() {
        count = DEFAULT_SIZE;
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            pool.offer(new Room());
        }
    }


    private static Room initRoom() {
        // 双重检查
        if (count < DEFAULT_SIZE) {
            synchronized (RoomPool.class) {
                if (count < DEFAULT_SIZE) {
                    ++count;
                    return new Room();
                }
            }
        }
        return null;
    }

    /**
     * @return 从对象池获取对象
     */
    static Room acquireRoom() {
        synchronized (pool) {
            // 已创建完所有对象
            if (count >= DEFAULT_SIZE) {
                if (pool.isEmpty()) {
                    return null;
                } else {
                    return pool.poll();
                }
            }
            if (!pool.isEmpty()) {
                return pool.poll();
            }
        }
        return initRoom();
    }

    static Room acquireRoom(String roomId) {
        Room room = acquireRoom();
        if (room != null) {
            room.setId(roomId);
        }
        return room;
    }

    /**
     * @param room 返还给对象池的对象
     */
    static void releaseRoom(Room room) {
        synchronized (pool) {
            pool.offer(room);
        }
    }

}
