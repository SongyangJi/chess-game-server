package com.jsy.chessgameserver.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jsy.chessgameserver.dto.GameMessage;
import com.jsy.chessgameserver.dto.chess.ChessOperation;
import com.jsy.chessgameserver.dto.chess.GameRoomInfo;
import com.jsy.chessgameserver.dto.chess.Role;
import com.jsy.chessgameserver.dto.chess.State;
import com.jsy.chessgameserver.service.chess.GameRoom;
import com.jsy.chessgameserver.service.GameRoomService;
import com.jsy.chessgameserver.util.SpringUtils;
import com.jsy.chessgameserver.websocket.TopicEndpoint;
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
//        for(int i = 0;i<20;i++) {
//            list.add(new GameRoomInfo("room"+i, new Role("", "张三", 0), new Role("", "李四", 1),null));
//        }
        return list;
    }

    @Override
    public boolean setReadyStateOfRoomRole(String roomId, Role role) {
        GameRoom gameRoom = repository.get(roomId);
        gameRoom.setReadyState(role);
        // 如果双方都准备好，才可以下棋
        if (gameRoom.getGameState().getState() == State.RUNNING) {
            GameMessage gameMessage = new GameMessage(roomId, null, null, ChessOperation.START);
            try {
                TopicEndpoint.broadcastMessage(roomId, gameMessage);
            } catch (JsonProcessingException e) {
//                e.printStackTrace();
                log.warn("信息发送失败");
                return false;
            }
            log.info("房间 roomId {} 游戏开始", roomId);
        }
        return true;
    }
}
