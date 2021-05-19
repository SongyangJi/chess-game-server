package com.jsy.chessgameserver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

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
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

    public ChatMessage(String from, String to, String message, Date date) {
        super(from, to);
        this.message = message;
        this.date = date;
    }

    public ChatMessage(String from, String to, String message) {
        super(from, to);
        this.message = message;
        this.date = new Date();
    }
}

/*
{
  "from": "",
  "to": "",
  "message": ""
}
 */