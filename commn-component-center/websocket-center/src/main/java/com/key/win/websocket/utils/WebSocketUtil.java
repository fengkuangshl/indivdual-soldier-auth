package com.key.win.websocket.utils;

import com.key.win.basic.util.JsonUtils;
import com.key.win.basic.web.Result;

import javax.websocket.Session;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.Future;


public class WebSocketUtil {
    /**
     * 发送消息
     */
    public static boolean sendMessage(Session session, Object message) {
        try {
            String msg = JsonUtils.toJsonNoException(Result.succeed(message, ""));
            session.getBasicRemote().sendText(msg);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 异步发送消息
     */
    public static boolean sendMessageAsync(Session session, Object message) {
        String msg = JsonUtils.toJsonNoException(Result.succeed(message, ""));
        Future<Void> voidFuture = session.getAsyncRemote().sendText(msg);
        return voidFuture.isDone();
    }

    /**
     * 发送字节消息
     */
    public static boolean sendBytes(Session session, byte[] bytes) {
        try {
            session.getBasicRemote().sendBinary(ByteBuffer.wrap(bytes));
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 异步发送字节
     */
    public static boolean sendBytesAsync(Session session, byte[] bytes) {
        Future<Void> voidFuture = session.getAsyncRemote().sendBinary(ByteBuffer.wrap(bytes));
        return voidFuture.isDone();
    }
    /**
     * 发送对象消息
     */
    public static boolean sendObject(Session session, Object o) {
        try {
            session.getBasicRemote().sendObject(Result.succeed(o, ""));
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 异步发送对象
     */
    public static boolean sendObjectAsync(Session session, Object o) {
        Future<Void> voidFuture = session.getAsyncRemote().sendObject(Result.succeed(o, ""));
        return voidFuture.isDone();
    }
}
