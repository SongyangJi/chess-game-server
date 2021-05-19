package com.jsy.chessgameserver.dto.chess;

import com.jsy.chessgameserver.dto.Message;
import lombok.*;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameMessage extends Message {
    Dot dot;
    Role role;
    ChessOperation opt;
}
