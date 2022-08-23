package com.key.win.websocket.config;


import com.key.win.websocket.manager.WebSocketManager;
import com.key.win.websocket.manager.impl.MemoryWebSocketManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix = "spring.web.socket", name = "cluster", havingValue = "false")
@Configuration
public class MemoryWebSocketConfig {


    @Bean(WebSocketManager.WEBSOCKET_MANAGER_NAME)
    @ConditionalOnMissingBean(name = WebSocketManager.WEBSOCKET_MANAGER_NAME)
    public WebSocketManager webSocketManager() {
        return new MemoryWebSocketManager();
    }


}