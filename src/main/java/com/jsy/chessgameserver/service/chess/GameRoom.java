package com.jsy.chessgameserver.service.chess;

import com.jsy.chessgameserver.dto.chess.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
    private GameState gameState;

    private boolean gameOver = false;

    private boolean ownerReady = false, challengerReady = false;

    @Resource
    private ChessBoard chessBoard;

    @PostConstruct
    public void init() {
        gameState = new GameState();
        gameState.setState(State.READY);
    }


    /**
     * 下棋
     *
     * @param dot
     * @return
     */
    public boolean playChess(Dot dot) {
        int x = dot.getX();
        int y = dot.getY();
        int color = dot.getColor();

        if (chessBoard.occupy(x, y, color)) {
            gameOver = chessBoard.checkWin(x, y);
            if (gameOver) {
                // 游戏结束
                gameState.setState(State.FINISH);
                gameState.setWinner(toRole(dot.getColor()));
            }
            return true;
        }
        return false;
    }

    /**
     * 游戏是否结束
     *
     * @return
     */
    public boolean isGameOver() {
        return gameOver;
    }

    private Role toRole(int turn) {
        return turn == 0 ? owner : challenger;
    }

    /**
     * 返回赢家
     *
     * @return
     */
    public Role getWinner() {
        if (gameOver) {
            return gameState.getWinner();
        }
        return null;
    }

    /**
     * 获取房间信息
     *
     * @return
     */
    public GameRoomInfo getRoomInfo() {
        return new GameRoomInfo(roomId, owner, challenger, gameState);
    }


    /**
     * @param role
     */
    public void setReadyState(Role role) {
        if (role == null || role.getUid() == null || role.getUid().equals("")) {
            return;
        }
        if (role.equals(owner)) {
            ownerReady = true;
        } else if (role.equals(challenger)) {
            challengerReady = true;
        }
        if (ownerReady && challengerReady) {
            // 游戏开始
            gameState.setState(State.RUNNING);
        }
    }

}
