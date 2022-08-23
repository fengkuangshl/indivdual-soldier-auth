package com.key.win.websocket.redis.action;


import com.alibaba.fastjson.JSONObject;
import com.key.win.websocket.WebSocket;
import com.key.win.websocket.manager.WebSocketManager;
import com.key.win.websocket.utils.WebSocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class SendMessageAction implements Action {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void doMessage(WebSocketManager manager, JSONObject object) {
        if (!object.containsKey(USER_NAME)) {
            return;
        }
        if (!object.containsKey(MESSAGE)) {
            return;
        }

        String userName = object.getString(USER_NAME);

        List<WebSocket> list = manager.getList(userName);
        if (list == null || list.size() == 0) {
            logger.error("[{}]对应的webscoket集合为空！", userName);
            return;
        }
        for (WebSocket webSocket : list) {
            WebSocketUtil.sendMessageAsync(webSocket.getSession(), object.get(MESSAGE));
        }
    }
}
