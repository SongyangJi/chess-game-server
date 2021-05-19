package com.jsy.chessgameserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Message {
    private String from;
    private String to;

}

/*
{   "from": "李四",   "to": "张三" }
 */