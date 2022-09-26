package com.key.win.auth.websocket.interceptor;

import com.key.win.basic.util.JsonUtils;
import com.key.win.basic.web.Result;
import com.key.win.rsa.RSAEncryptor;
import com.key.win.rsa.exception.BizEncryptException;
import com.key.win.rsa.util.RSAUtils;
import com.key.win.rsa.util.SignUtils;
import com.key.win.rsa.web.EncryptModel;
import com.key.win.websocket.interceptor.impl.AbstractMessageSendProcessImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;

@Service
public class DeviceMessageSendProcessImpl extends AbstractMessageSendProcessImpl {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String sendTextBefore(Object object) {
        EncryptModel out = new EncryptModel();
        out.setTimestamp(System.currentTimeMillis());
        try {
            RSAEncryptor rsaEncryptor = new RSAEncryptor(RSAUtils.privateKeyPath, RSAUtils.publicKeyPath);
            out.setData(rsaEncryptor.encryptWithBase64(JsonUtils.toJsonNoException(object)));
            String rawSign = String.format("data=%s&timestamp=%d", out.getData(), out.getTimestamp());
            out.setSign(SignUtils.sha(rawSign));
        } catch (Exception e) {
            logger.error("参数签名失败:" + e.getMessage(), e);
            throw new BizEncryptException("参数签名失败!");
        }
        return JsonUtils.toJsonNoException(out);
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
        return "/ws/device/{androidId}/{serialNumber}";
    }
}
