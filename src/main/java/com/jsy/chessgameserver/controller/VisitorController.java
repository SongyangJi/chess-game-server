package com.jsy.chessgameserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

@RestController
@RequestMapping("/visitor")
public class VisitorController {

    @GetMapping("/{nickname}")
    public String login(@PathVariable String nickname) {
        return UUID.randomUUID().toString();
    }

}
