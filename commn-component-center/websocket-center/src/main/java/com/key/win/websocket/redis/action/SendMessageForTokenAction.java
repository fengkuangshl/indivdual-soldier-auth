package com.key.win.websocket.redis.action;


import com.alibaba.fastjson.JSONObject;
import com.key.win.websocket.WebSocket;
import com.key.win.websocket.manager.WebSocketManager;
import com.key.win.websocket.utils.WebSocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SendMessageForTokenAction implements Action {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void doMessage(WebSocketManager manager, JSONObject object) {
        if (!object.containsKey(USER_NAME)) {
            return;
        }
        if (!object.containsKey(MESSAGE)) {
            return;
        }

        String token = object.getString(TOKEN);

        WebSocket webSocket = manager.get(token);
        if (webSocket == null) {
            logger.error("[{}]没有找到对应的websocket!", token);
            return;
        }
        WebSocketUtil.sendMessageAsync(webSocket.getSession(), object.get(MESSAGE));
    }
}
