package com.jsy.chessgameserver.service.impl;

import com.jsy.chessgameserver.dto.chess.Role;
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

@Service("GameRoomPool")
@Slf4j
public class GameRoomServiceImpl implements GameRoomService {

    PriorityBlockingQueue<String> queue;

    Map<String, GameRoom> repository;

    public GameRoomServiceImpl() {
        queue = new PriorityBlockingQueue<>();
        for (int i = 1; i <= 10; i++) {
            queue.offer(String.valueOf(i));
        }
        repository = new ConcurrentHashMap<>();
        log.info("GameRoomPool实例创建");
    }

    @Override
    public GameRoom getRoom(String roomId) {
        return repository.get(roomId);
    }

    @Override
    public String createRoom() {
        String roomId = queue.poll();
        if (roomId != null) {
            GameRoom gameRoom = SpringUtils.getBean(GameRoom.class);
            assert gameRoom != null;
            gameRoom.setRoomId(roomId);
            repository.put(roomId, gameRoom);
        }
        return roomId;
    }

    @Override
    public List<GameRoom.RoomInfo> getRoomList() {
        var list = new ArrayList<GameRoom.RoomInfo>();
        repository.values().forEach(gameRoom -> list.add(gameRoom.getRoomInfo()));
        var roomInfo = new GameRoom.RoomInfo("room1",new Role("","张三",0),null);
//        System.out.println(roomInfo);
        list.add(roomInfo);
        return list;
    }
}
