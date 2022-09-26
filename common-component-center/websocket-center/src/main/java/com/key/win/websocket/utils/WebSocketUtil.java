package com.key.win.websocket.utils;

import com.key.win.basic.util.JsonUtils;
import com.key.win.basic.web.Result;
import com.key.win.websocket.interceptor.GetMessageSendProcess;
import com.key.win.websocket.interceptor.IMessageSendProcess;

import javax.websocket.Session;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.Future;


public class WebSocketUtil {
    /**
     * 发送消息
     */
    public static boolean sendMessage(Session session, Object message) {
        IMessageSendProcess instance = GetMessageSendProcess.getInstance(session);
        try {
            //String msg = JsonUtils.toJsonNoException(Result.succeed(message, ""));
            String text = instance.sendTextBefore(message);
            session.getBasicRemote().sendText(text);
            instance.sendTextAfter(text);
            return true;
        } catch (IOException e) {
            instance.messageSendException(message, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 异步发送消息
     */
    public static boolean sendMessageAsync(Session session, Object message) {
        IMessageSendProcess instance = GetMessageSendProcess.getInstance(session);
        String text = instance.sendTextBefore(message);
        //String msg = JsonUtils.toJsonNoException(Result.succeed(message, ""));
        Future<Void> voidFuture = session.getAsyncRemote().sendText(text);
        boolean done = voidFuture.isDone();
        instance.sendTextAfter(text);
        return done;
    }

    /**
     * 发送字节消息
     */
    public static boolean sendBytes(Session session, byte[] bytes) {
        IMessageSendProcess instance = GetMessageSendProcess.getInstance(session);
        try {
            ByteBuffer byteBuffer = instance.sendBytesBefore(bytes);
            session.getBasicRemote().sendBinary(byteBuffer);
            instance.sendBytesAfter(bytes);
            return true;
        } catch (IOException e) {
            instance.messageSendException(bytes, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 异步发送字节
     */
    public static boolean sendBytesAsync(Session session, byte[] bytes) {
        IMessageSendProcess instance = GetMessageSendProcess.getInstance(session);
        ByteBuffer byteBuffer = instance.sendBytesBefore(bytes);
        Future<Void> voidFuture = session.getAsyncRemote().sendBinary(byteBuffer);
        boolean done = voidFuture.isDone();
        instance.sendBytesAfter(bytes);
        return done;
    }

    /**
     * 发送对象消息
     */
    public static boolean sendObject(Session session, Object o) {
        IMessageSendProcess instance = GetMessageSendProcess.getInstance(session);
        try {
            Object object = instance.sendObjectBefore(o);
            session.getBasicRemote().sendObject(object);
            instance.sendObjectAfter(o);
            return true;
        } catch (Exception e) {
            instance.messageSendException(o, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 异步发送对象
     */
    public static boolean sendObjectAsync(Session session, Object o) {
        IMessageSendProcess instance = GetMessageSendProcess.getInstance(session);
        Object object = instance.sendObjectBefore(o);
        Future<Void> voidFuture = session.getAsyncRemote().sendObject(object);
        boolean done = voidFuture.isDone();
        instance.sendObjectAfter(o);
        return done;
    }
}
