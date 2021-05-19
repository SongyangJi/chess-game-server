package com.jsy.chessgameserver.controller;

import com.jsy.chessgameserver.dto.chess.ChessOperation;
import com.jsy.chessgameserver.dto.chess.GameMessage;
import com.jsy.chessgameserver.dto.endecoder.GameMessageDecoder;
import com.jsy.chessgameserver.dto.endecoder.GameMessageEncoder;
import com.jsy.chessgameserver.service.chess.GameRoom;
import com.jsy.chessgameserver.service.GameRoomService;
import com.jsy.chessgameserver.websocket.TopicEndpoint;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */
@ServerEndpoint(value = "/topic/{roomId}",
        decoders = {
                GameMessageDecoder.class},
        encoders = {
                GameMessageEncoder.class
        })
@Controller
public class ChessGameSocketController extends TopicEndpoint {

    public static GameRoomService gameRoomService;

    public GameRoom gameRoom;

    @OnOpen
    @Override
    public void open(@PathParam("roomId") @NonNull String roomId, Session session) {
        super.open(roomId, session);
        gameRoom = gameRoomService.getRoom(roomId);
    }

    @OnMessage
    public void broker(GameMessage message) {
        switch (message.getOpt()) {
            //  请求移动
            case MOVE:
                if (gameRoom.playChess(message.getDot())) {
                    GameMessage stepMessage = new GameMessage(message.getDot(), message.getRole(), ChessOperation.STEP);
                    broker(stepMessage);
                    if (gameRoom.isGameOver()) {
                        GameMessage overMessage = new GameMessage(null, message.getRole(), ChessOperation.OVER);
                        broadcastMessage(overMessage);
                    }
                }
                break;
            case STEP:
                break;
            default:
                break;
        }
        broadcastMessage(message);
    }

    @Autowired
    public static void setGameRoomService(GameRoomService gameRoomService) {
        ChessGameSocketController.gameRoomService = gameRoomService;
    }
}
