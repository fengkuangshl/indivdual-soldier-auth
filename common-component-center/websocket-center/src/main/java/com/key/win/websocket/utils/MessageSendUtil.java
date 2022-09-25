package com.key.win.websocket.utils;

import com.key.win.basic.util.JsonUtils;
import com.key.win.websocket.manager.WebSocketManager;
import com.key.win.websocket.vo.WebsocketBaseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class MessageSendUtil {
    private static final Logger logger = LoggerFactory.getLogger(MessageSendUtil.class);

    public static final String WEBSOCKET_BASE_MESSAGE = "WebSocketBaseMessage";

    private static TaskExecutor taskExecutor;

    private static WebSocketManager webSocketManager;

    @Autowired
    public void setWebSocketManager(WebSocketManager webSocketManager) {
        MessageSendUtil.webSocketManager = webSocketManager;
    }

    @Autowired
    public void setTaskExecutor(TaskExecutor taskExecutor) {
        MessageSendUtil.taskExecutor = taskExecutor;
    }

    public static void broadcast(String action, String mapper, Object message) {
        WebsocketBaseMessage websocketBaseMessage = buildWebsocketBaseMessage(action, mapper, message);
        webSocketManager.broadcast(websocketBaseMessage);
    }

    public static void broadcast(Object message) {
        webSocketManager.broadcast(message);
    }

    public static void sendMessage(String action, String mapper, Object message, String token) {
        WebsocketBaseMessage websocketBaseMessage = buildWebsocketBaseMessage(action, mapper, message);
        webSocketManager.sendMessageByToken(token, websocketBaseMessage);
    }

    public static void sendMessage(String action, String mapper, Object message, List<String> tokens) {
        WebsocketBaseMessage websocketBaseMessage = buildWebsocketBaseMessage(action, mapper, message);
        sendMessage(websocketBaseMessage, tokens);
    }

    public static void sendMessage(Object message, String token) {
        webSocketManager.sendMessageByToken(token, message);
    }

    public static void sendMessage(Object message, List<String> tokens) {
        if (!CollectionUtils.isEmpty(tokens)) {
            for (String token : tokens) {
                webSocketManager.sendMessageByToken(token, message);
            }
        }
    }

    protected static WebsocketBaseMessage buildWebsocketBaseMessage(String action, String mapper, Object message) {
        WebsocketBaseMessage websocketBaseMessage = new WebsocketBaseMessage();
        websocketBaseMessage.setAction(action);
        websocketBaseMessage.setMapper(mapper);
        if (message instanceof String) {
            websocketBaseMessage.setMessage((String) message);
        } else {
            websocketBaseMessage.setMessage(JsonUtils.toJsonNoException(message));
        }

        return websocketBaseMessage;
    }
}
