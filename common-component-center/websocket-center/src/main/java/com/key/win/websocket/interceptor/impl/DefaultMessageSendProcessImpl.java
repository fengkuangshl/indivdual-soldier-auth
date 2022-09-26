package com.key.win.websocket.interceptor.impl;

import com.key.win.basic.util.JsonUtils;
import com.key.win.basic.web.Result;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;

@Service
public class DefaultMessageSendProcessImpl extends AbstractMessageSendProcessImpl {


    @Override
    public String sendTextBefore(Object object) {
        return JsonUtils.toJsonNoException(Result.succeed(object, ""));
    }

    @Override
    public String sendTextAfter(String text) {
        return text;
    }

    @Override
    public ByteBuffer sendBytesBefore(byte[] bytes) {
        return ByteBuffer.wrap(bytes);
    }

    @Override
    public byte[] sendBytesAfter(byte[] bytes) {
        return bytes;
    }

    @Override
    public Object sendObjectBefore(Object object) {
        return object;
    }

    @Override
    public Object sendObjectAfter(Object object) {
        return object;
    }

    @Override
    public void messageSendException(Object object, Exception e) {

    }

    @Override
    public String getId() {
        return "/ws/{token}";
    }
}
