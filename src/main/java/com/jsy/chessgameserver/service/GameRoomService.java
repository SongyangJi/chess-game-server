package com.jsy.chessgameserver.service;

import com.jsy.chessgameserver.service.chess.GameRoom;

import java.util.List;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

public interface GameRoomService {

    GameRoom getRoom(String roomId);

    String createRoom();

    List<GameRoom.RoomInfo> getRoomList();

}
