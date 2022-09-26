package com.key.win.websocket.interceptor;

import com.key.win.websocket.interceptor.impl.DefaultMessageSendProcessImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GetMessageSendProcess {

    private static final Logger logger = LoggerFactory.getLogger(GetMessageSendProcess.class);
    private static final Map<String, IMessageSendProcess> instances = new ConcurrentHashMap<>();


    public static IMessageSendProcess getInstance(Session session) {
        if (instances.size() > 0) {
            AntPathMatcher antPathMatcher = new AntPathMatcher();
            for (String key : instances.keySet()) {
                boolean b = antPathMatcher.match( key,session.getRequestURI().getPath());
                if (b) {
                    logger.info("匹配到的路径:{}", key);
                    return instances.get(key);
                }
            }
        }
        return new DefaultMessageSendProcessImpl();
    }

    public static void putInstance(String key, IMessageSendProcess iMessageSendProcess) {
        instances.put(key, iMessageSendProcess);
    }


}
