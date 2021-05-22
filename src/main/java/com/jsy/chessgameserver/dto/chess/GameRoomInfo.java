package com.jsy.chessgameserver.dto.chess;

import lombok.*;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description: 游戏房间信息
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameRoomInfo {
    private String roomId;
    private Role owner, challenger;
    private GameState gameState;
}