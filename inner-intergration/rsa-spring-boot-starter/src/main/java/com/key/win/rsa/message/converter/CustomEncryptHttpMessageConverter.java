package com.key.win.rsa.message.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.key.win.rsa.RSAEncryptor;
import com.key.win.rsa.util.RSAUtils;
import com.key.win.rsa.web.IEncryptor;
import com.key.win.rsa.util.SignUtils;
import com.key.win.rsa.web.EncryptModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@RequiredArgsConstructor
public class CustomEncryptHttpMessageConverter extends MappingJackson2HttpMessageConverter {

    private final ObjectMapper objectMapper;


    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        if (type instanceof Class) {
            Class<?> clazz = (Class) type;
            if (IEncryptor.class.isAssignableFrom(clazz)) {
                return converterEntity(inputMessage, clazz);
            }
        }
        return super.read(type, contextClass, inputMessage);
    }

    private Object converterEntity(HttpInputMessage inputMessage, Class<?> clazz) throws IOException {
        EncryptModel in = objectMapper.readValue(StreamUtils.copyToByteArray(inputMessage.getBody()), EncryptModel.class);
        String inRawSign = String.format("data=%s&timestamp=%d", in.getData(), in.getTimestamp());
        String inSign;
        try {
            inSign = SignUtils.sha(inRawSign);
        } catch (Exception e) {
            logger.error("解密失败:" + e.getMessage(), e);
            throw new IllegalArgumentException("验证参数签名失败!");
        }
        if (!inSign.equals(in.getSign())) {
            logger.error("验证参数签名失败");
            throw new IllegalArgumentException("验证参数签名失败!");
        }
        try {
            RSAEncryptor rsaEncryptor = new RSAEncryptor(RSAUtils.privateKeyPath, RSAUtils.publicKeyPath);
            return objectMapper.readValue(rsaEncryptor.encryptWithBase64(in.getData()), clazz);
        } catch (Exception e) {
            logger.error("解密失败:" + e.getMessage(), e);
            throw new IllegalArgumentException("解密失败!");
        }
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws
            IOException, HttpMessageNotReadableException {
        if (IEncryptor.class.isAssignableFrom(clazz)) {
            return converterEntity(inputMessage, clazz);
        } else {
            return super.readInternal(clazz, inputMessage);
        }
    }

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws
            IOException, HttpMessageNotWritableException {
        if (type instanceof Class) {
            Class<?> clazz = (Class) type;
            if (IEncryptor.class.isAssignableFrom(clazz)) {
                EncryptModel out = new EncryptModel();
                out.setTimestamp(System.currentTimeMillis());
                try {
                    RSAEncryptor rsaEncryptor = new RSAEncryptor(RSAUtils.privateKeyPath, RSAUtils.publicKeyPath);
                    out.setData(rsaEncryptor.encryptWithBase64(objectMapper.writeValueAsString(object)));
                    String rawSign = String.format("data=%s&timestamp=%d", out.getData(), out.getTimestamp());
                    out.setSign(SignUtils.sha(rawSign));
                    object = out;
                } catch (Exception e) {
                    logger.error("参数签名失败:" + e.getMessage(), e);
                    throw new IllegalArgumentException("参数签名失败!");
                }
            }
        }
        super.writeInternal(object, type, outputMessage);
    }
}