package com.key.win.websocket.endpoint;


import com.key.win.websocket.config.GetHttpSessionConfigurator;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint(value = "/ws/{token}", configurator = GetHttpSessionConfigurator.class)
public class WebSocketEndpoint extends BaseWebSocketEndpoint {

    @OnOpen
    public void onOpen(Session session, @PathParam(TOKEN) String token, EndpointConfig config) {
        try {
            logger.info("*** WebSocket opened from sessionId " + session.getId() + " , token = " + token);
            connect(session,token);
        } catch (Exception ex) {
            logger.error("建立webSocket出错:", ex);
        }

    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam(TOKEN) String token) {
        logger.info("接收到的数据为：" + message + " from sessionId " + session.getId() + " , token = " + token);
        receiveMessage(message, session,token);
    }

    @OnClose
    public void onClose(Session session, @PathParam(TOKEN) String token) {
        logger.info("*** WebSocket closed from sessionId " + session.getId() + " , token = " + token);
        disconnect(token);
    }

    @OnError
    public void onError(Throwable t, @PathParam(TOKEN) String token) {
        logger.info("发生异常：, token = " + token);
        logger.error(t.getMessage(), t);
        disconnect(token);
    }
}
