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

@Getter
@Setter
@NoArgsConstructor
public class ChatMessage extends Message {

    private String sender;

    private String content;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

    @Override
    public String toString() {
        return "ChatMessage{" +
                "sender='" + sender + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}

/*
{
  "sender": "",
  "content": "",
  "time": "2021-05-28 04:23:18",
  "from": "",
  "to": ""
}
 */