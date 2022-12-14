package com.key.win.websocket.redis.action;


import com.alibaba.fastjson.JSONObject;
import com.key.win.basic.util.IndividualSoldierAuthConstantUtils;
import com.key.win.websocket.manager.WebSocketManager;


public interface Action {
    String TOKEN = IndividualSoldierAuthConstantUtils.TOKEN;
    String USER_NAME = "userName";
    String MESSAGE = "message";
    String ACTION = "action";

    void doMessage(WebSocketManager manager, JSONObject object);
}
