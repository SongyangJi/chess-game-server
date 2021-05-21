package com.jsy.chessgameserver.dto.chess;

import lombok.*;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoomInfo {
    private String roomId;
    private Role owner, challenger;
    private int win;
    private State state;
}