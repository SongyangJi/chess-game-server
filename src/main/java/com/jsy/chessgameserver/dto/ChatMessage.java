package com.jsy.chessgameserver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

@ToString
@Getter
@Setter
@NoArgsConstructor
public class ChatMessage extends Message {
    String from;
    String to;
    String message;

    public ChatMessage(String from, String to, String message) {
        super(from, to);
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