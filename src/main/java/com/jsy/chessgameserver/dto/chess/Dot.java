package com.jsy.chessgameserver.dto.chess;

import lombok.*;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

@Data
public class Dot {
    // 坐标
    int x, y;
    // 颜色
    int color;
}
