package com.jsy.chessgameserver.dto.chess;

import lombok.*;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Role {
    String uid;
    String nickName;
    int turn;
}
