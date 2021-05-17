package com.jsy.chessgameserver.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

@Service
@Scope("prototype")
public class ChessGame {


    boolean gameOver = false, started = false;

    int turn;

    void changeTurn() {
        turn = 1 - turn;
    }

    @Resource
    private ChessBoard chessBoard;

    public boolean playChess(int x, int y, int color) {
        if (turn != color) {
            return false;
        }
        if (chessBoard.occupy(x, y, color)) {
            gameOver = chessBoard.checkWin(x,y);
            return true;
        }
        return false;
    }


    public boolean isStarted() {
        return started;
    }

    public boolean isGameOver() {
        return gameOver;
    }


}
