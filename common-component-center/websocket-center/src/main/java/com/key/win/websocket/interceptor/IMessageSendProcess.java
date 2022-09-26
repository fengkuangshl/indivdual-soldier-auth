package com.key.win.websocket.interceptor;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public interface IMessageSendProcess {

    String sendTextBefore(Object object);

    String sendTextAfter(String text);

    ByteBuffer sendBytesBefore(byte[] bytes);

    byte[] sendBytesAfter(byte[] bytes);

    Object sendObjectBefore(Object object);

    Object sendObjectAfter(Object object);

    void messageSendException(Object object,Exception e);
}
