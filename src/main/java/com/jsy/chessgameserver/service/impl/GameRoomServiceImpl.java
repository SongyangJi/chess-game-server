package com.jsy.chessgameserver.service.impl;

import com.jsy.chessgameserver.dto.chess.GameRoomInfo;
import com.jsy.chessgameserver.service.chess.GameRoom;
import com.jsy.chessgameserver.service.GameRoomService;
import com.jsy.chessgameserver.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

@Service("gameRoomService")
@Slf4j
public class GameRoomServiceImpl implements GameRoomService {

    PriorityBlockingQueue<Integer> queue;

    Map<String, GameRoom> repository;

    public GameRoomServiceImpl() {
        queue = new PriorityBlockingQueue<>();
        for (int i = 1; i <= 100; i++) {
            queue.offer(i);
        }
        repository = new ConcurrentHashMap<>();
        log.info("gameRoomService实例创建");
    }

    @Override
    public GameRoom getRoom(String roomId) {
        return repository.get(roomId);
    }

    @Override
    public String createRoom() {
        Integer id = queue.poll();
        String roomId = id != null ? String.valueOf(id) : null;
        if (roomId != null) {
            GameRoom gameRoom = SpringUtils.getBean(GameRoom.class);
            assert gameRoom != null;
            gameRoom.setRoomId(roomId);
            repository.put(roomId, gameRoom);
        }
        return roomId;
    }

    @Override
    public List<GameRoomInfo> getRoomList() {
        var list = new ArrayList<GameRoomInfo>();
        repository.values().forEach(gameRoom -> list.add(gameRoom.getRoomInfo()));
//        System.out.println(roomInfo);
//        list.add(new GameRoomInfo("room1", new Role("", "张三", 0), new Role("", "李四", 1), State.READY));
//        list.add(new GameRoomInfo("room2", new Role("", "张三", 0), new Role("", "李四", 1), State.START));
//        list.add(new GameRoomInfo("room3", new Role("", "张三", 0), new Role("", "李四", 1), State.FINISH));
//        list.add(new GameRoomInfo("room4", new Role("", "张三", 0), new Role("", "李四", 1), State.RUNNING));
        return list;
    }
}
