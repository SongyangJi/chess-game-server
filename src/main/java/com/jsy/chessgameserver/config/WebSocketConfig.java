package com.jsy.chessgameserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:  WebSocket的配置类
 */

@Configuration
public class WebSocketConfig {
    /**
     * 自动注册使用了@ServerEndpoint注解声明的Websocket Endpoint
     *
     * 注意：如果你使用了独立的servlet容器，如Tomcat，而不是Spring内置的，就不要这个bean了，
     *      因为它将由容器自己提供和管理
     *
     * @return ServerEndpointExporter
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
