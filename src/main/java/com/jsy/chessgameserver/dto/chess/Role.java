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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return uid.equals(role.uid);
    }

    @Override
    public int hashCode() {
        return uid.hashCode();
    }
}
