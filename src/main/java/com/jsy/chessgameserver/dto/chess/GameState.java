package com.jsy.chessgameserver.dto.chess;

import lombok.Data;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

@Data
public class GameState {
    // 对局状态的枚举量
    State state;
    // 赢家
    Role winner;
}
