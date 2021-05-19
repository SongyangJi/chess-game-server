package com.jsy.chessgameserver.controller;

import com.jsy.chessgameserver.dto.chess.Role;
import com.jsy.chessgameserver.service.GameRoomService;
import com.jsy.chessgameserver.service.chess.GameRoom;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

@RestController
@RequestMapping("/game")
public class ChessGameController {

    @Resource(name = "GameRoomPool")
    GameRoomService gameRoomService;

    @GetMapping("/room")
    public ResponseEntity<String> createRoom() {
        return ResponseEntity.ok(gameRoomService.createRoom());
    }

    @GetMapping("/room/room-list")
    public ResponseEntity<List<GameRoom.RoomInfo>> getRoomList() {
        return ResponseEntity.ok(gameRoomService.getRoomList());
    }

    @PostMapping("/room/{roomId}")
    public ResponseEntity<String> setGameRole(@RequestBody Role role, @PathVariable String roomId) {
        if(gameRoomService.getRoom(roomId) == null){
            return ResponseEntity.badRequest().body("无此房间");
        }
        GameRoom gameRoom = gameRoomService.getRoom(roomId);
        if (role.getTurn() == 0) {
            gameRoom.setOwner(role);
            return ResponseEntity.ok().body("擂主身份");
        } else if (role.getTurn() == 1) {
            gameRoom.setChallenger(role);
            return ResponseEntity.ok().body("挑战者身份");
        } else {
            return ResponseEntity.badRequest().body("角色类型错误");
        }
    }

}
