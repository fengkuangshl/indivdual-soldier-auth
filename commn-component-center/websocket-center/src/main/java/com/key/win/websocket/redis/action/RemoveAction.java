package com.key.win.websocket.redis.action;


import com.alibaba.fastjson.JSONObject;
import com.key.win.websocket.manager.WebSocketManager;


public class RemoveAction implements Action {
    @Override
    public void doMessage(WebSocketManager manager, JSONObject object) {
        if (!object.containsKey(TOKEN)) {
            return;
        }

        String token = object.getString(TOKEN);
        manager.remove(token);
    }
}
