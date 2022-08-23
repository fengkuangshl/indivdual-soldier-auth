package com.key.win.websocket.manager.impl;


import com.alibaba.fastjson.JSONObject;
import com.key.win.websocket.WebSocket;
import com.key.win.websocket.redis.action.Action;
import com.key.win.websocket.redis.action.SendMessageAction;
import com.key.win.websocket.redis.action.SendMessageForTokenAction;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;


public class RedisWebSocketManager extends MemoryWebSocketManager {
    public static final String CHANNEL = "websocket";
    protected RedisTemplate<String, Object> redisTemplate;

    public RedisWebSocketManager(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public void put(String token, WebSocket webSocket) {
        super.put(token, webSocket);
    }

    @Override
    public void remove(String token) {
        super.remove(token);
    }

    @Override
    public void sendMessageByUserName(String userName, Object message) {
        sendMessageToRedisChannel(SendMessageAction.class.getName(), Action.USER_NAME, userName, message);
    }

    @Override
    public void sendMessageByToken(String token, Object message) {
        sendMessageToRedisChannel(SendMessageForTokenAction.class.getName(), Action.TOKEN, token, message);
    }

    private void sendMessageToRedisChannel(String className, String key, String value, Object message) {
        Map<String, Object> map = new HashMap<>(3);
        map.put(Action.ACTION, className);
        map.put(key, value);
        map.put(Action.MESSAGE, message);
        // 发布消息到redis频道上 redis转发到订阅的各个socket实例上 收到信息 根据标识 获取到session 发给自己对应的客户端
        redisTemplate.convertAndSend(getChannel(), new JSONObject(map).toString());
    }


    protected String getChannel() {
        return CHANNEL;
    }
}
