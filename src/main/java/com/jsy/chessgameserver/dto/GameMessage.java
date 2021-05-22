package com.jsy.chessgameserver.dto;

import com.jsy.chessgameserver.dto.Message;
import com.jsy.chessgameserver.dto.chess.ChessOperation;
import com.jsy.chessgameserver.dto.chess.Dot;
import com.jsy.chessgameserver.dto.chess.Role;
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
    private String roomId;
    private Dot dot;
    private Role role;
    private ChessOperation opt;
}

/*
{
  "roomId": "",
  "dot": {
    "x": 0,
    "y": 0,
    "color": 0
  },
  "role": {
    "uid": "",
    "nickName": "",
    "turn": 0
  },
  "opt": "MOVE",
  "from": "",
  "to": ""
}
 */
