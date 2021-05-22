package com.jsy.chessgameserver.service.chess;

import com.jsy.chessgameserver.dto.chess.*;
import lombok.Getter;
import lombok.Setter;
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
public class GameRoom {

    @Getter
    @Setter
    private String roomId;

    @Getter
    @Setter
    private Role owner, challenger;

    @Getter
    @Setter
    private GameState gameState;

    private boolean gameOver = false;


    private int turn = 0;

    private synchronized void changeTurn() {
        turn = 1 - turn;
    }


    @Resource
    private ChessBoard chessBoard;


    public boolean playChess(Dot dot) {
        int x = dot.getX();
        int y = dot.getY();
        int color = dot.getColor();
        if (turn != dot.getColor()) {
            return false;
        }
        if (chessBoard.occupy(x, y, color)) {
            changeTurn();
            gameOver = chessBoard.checkWin(x, y);
            return true;
        }
        return false;
    }


    public boolean isGameOver() {
        return gameOver;
    }

    public int getWin() {
        return chessBoard.getWin();
    }

    public GameRoomInfo getRoomInfo() {
        return new GameRoomInfo(roomId, owner, challenger, gameState);
    }

}
