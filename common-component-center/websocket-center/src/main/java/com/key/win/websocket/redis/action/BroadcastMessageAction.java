package com.key.win.websocket.redis.action;


import com.alibaba.fastjson.JSONObject;
import com.key.win.websocket.manager.WebSocketManager;
import com.key.win.websocket.utils.WebSocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BroadcastMessageAction implements Action {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void doMessage(WebSocketManager manager, JSONObject object) {
        if (!object.containsKey(MESSAGE)) {
            return;
        }

        if (manager.localWebSocketMap().values().size() == 0) {
            logger.error("websocket集合为空!");
            return;
        }
        manager.localWebSocketMap().values().forEach(
                webSocket -> WebSocketUtil.sendMessageAsync(
                        webSocket.getSession(), object.get(MESSAGE)));
    }
}
