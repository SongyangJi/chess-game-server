package com.jsy.chessgameserver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {
    String from;
    String to;
    String message;

    public ChatMessage(String from, String to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
    }
}

/*
{
  "from": "",
  "to": "",
  "message": ""
}
 */