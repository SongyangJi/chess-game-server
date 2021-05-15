package com.jsy.chessgameserver.websocket;

import lombok.Getter;
import lombok.Setter;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.Future;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

@Getter
public class Channel {

    private final Session session;
    private final String id;

    private Channel(Session session, String id) {
        this.session = session;
        this.id = id;
    }

    protected Channel(Session session) {
        this(session,session.getId());
    }



    void sendText(String text) throws IOException {
        session.getBasicRemote().sendText(text);
    }

    Future<Void> sendTextAsync(String text) {
        return session.getAsyncRemote().sendText(text);
    }

    void sendObject(Object object) throws IOException, EncodeException {
        session.getBasicRemote().sendObject(object);
    }

    Future<Void> sendObjectAsync(Object object) {
        return session.getAsyncRemote().sendObject(object);
    }

}
