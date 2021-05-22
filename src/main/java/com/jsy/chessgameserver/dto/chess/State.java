package com.jsy.chessgameserver.dto.chess;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

public enum State {
    START("即将开始"),
    READY("等待准备"),
    RUNNING("正在进行"),
    FINISH("已结束");

    String description;

    State(String description) {
        this.description = description;
    }
}

/*

{
  "START": "START",
  "READY": "READY",
  "RUNNING": "RUNNING",
  "FINISH": "FINISH",
}

 */
