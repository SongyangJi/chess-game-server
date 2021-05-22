package com.jsy.chessgameserver.controller;

import com.jsy.chessgameserver.dto.chess.ChessOperation;
import com.jsy.chessgameserver.dto.GameMessage;
import com.jsy.chessgameserver.dto.endecoder.GameMessageDecoder;
import com.jsy.chessgameserver.dto.endecoder.GameMessageEncoder;
import com.jsy.chessgameserver.service.chess.GameRoom;
import com.jsy.chessgameserver.service.GameRoomService;
import com.jsy.chessgameserver.websocket.TopicEndpoint;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

@Slf4j
@ServerEndpoint(value = "/topic/game/{roomId}",
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
        if (gameRoom == null) {
            try {
                session.close();
            } catch (IOException e) {
                log.error("session {} roomId 连接失败_{}", session, e.getMessage(), e);
            }
        }
    }

    @OnMessage
    public void broker(GameMessage message) {
        log.info("收到消息 {}", message);
        if (!message.getRoomId().equals(gameRoom.getRoomId())) {
            return;
        }
        String id = gameRoom.getRoomId();
        switch (message.getOpt()) {
            //  请求移动
            case MOVE:
                if (gameRoom.playChess(message.getDot())) {
                    GameMessage stepMessage = new GameMessage(id, message.getDot(), message.getRole(), ChessOperation.STEP);
                    broadcastMessage(stepMessage);
                    if (gameRoom.isGameOver()) {
                        GameMessage overMessage = new GameMessage(id, null, message.getRole(), ChessOperation.OVER);
                        broadcastMessage(overMessage);
                    }
                }
                break;
            case STEP:
                break;
            default:
                break;
        }
//        broadcastMessage(message);
    }

    @Autowired
    public void setGameRoomService(GameRoomService gameRoomService) {
        ChessGameSocketController.gameRoomService = gameRoomService;
    }
}
