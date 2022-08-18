package com.key.win.redis.util;

 
public interface RedisSubscribeCallback {
    void callback(String msg);
}
