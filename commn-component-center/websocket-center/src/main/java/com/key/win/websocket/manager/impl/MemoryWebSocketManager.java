package com.key.win.websocket.manager.impl;


import com.key.win.websocket.WebSocket;
import com.key.win.websocket.WebSocketEvent;
import com.key.win.websocket.manager.WebSocketManager;
import com.key.win.websocket.utils.WebSocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class MemoryWebSocketManager implements WebSocketManager, ApplicationContextAware {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 因为全局只有一个 WebSocketManager ，所以才敢定义为非static
     */
    private final Map<String, WebSocket> connections = new ConcurrentHashMap<>(100);
    private final Map<String, Set<String>> userName2Tokens = new ConcurrentHashMap<>(100);
    protected final ConnectionTimeOutChecker timeOutChecker = startChecker();


    /**
     * 同一用户 单点单点登录时使用
     *
     * @param token 标识
     * @return
     */
    @Override
    public WebSocket get(String token) {
        return connections.get(token);
    }

    @Override
    public List<WebSocket> getList(String userName) {
        return getConnectionForUser(userName);
    }

    @Override
    public void put(String token, WebSocket webSocket) {
        connections.put(token, webSocket);
        Set<String> set = userName2Tokens.get(webSocket.getUserName());
        if (set == null) {
            set = Collections.synchronizedSet(new HashSet<>());
            userName2Tokens.put(webSocket.getUserName(), set);
        }
        set.add(token);
        //发送连接事件
        getApplicationContext().publishEvent(new WebSocketEvent(webSocket, WebSocketEvent.EVENT_TYPE_OPEN));
    }

    @Override
    public void remove(String token) {
        WebSocket removedWebSocket = connections.remove(token);
        //发送关闭事件
        if (null != removedWebSocket) {
            userName2Tokens.get(removedWebSocket.getUserName()).remove(token);
            getApplicationContext().publishEvent(new WebSocketEvent(removedWebSocket, WebSocketEvent.EVENT_TYPE_CLOSE));
            removedWebSocket.closeSession();
        }
    }


    @Override
    public Map<String, WebSocket> localWebSocketMap() {
        return Collections.unmodifiableMap(connections);
    }

    @Override
    public int size() {
        Map<String, Set<String>> account2cons = Collections.unmodifiableMap(userName2Tokens);
        int count = (int) account2cons.entrySet().stream().filter(e -> e.getValue().size() > 0).count();
        return count;
    }

    @Override
    public void sendMessageByUserName(String userName, Object message) {
        List<WebSocket> list = getList(userName);
        //本地能找到就直接发
        if (list != null && list.size() > 0) {
            for (WebSocket webSocket : list) {
                WebSocketUtil.sendMessageAsync(webSocket.getSession(), message);
            }
            return;
        } else {
//            不能抛异常,如果用户没有登陆,会在登陆的时候再提示
//            throw new RuntimeException("userName 不存在");
            logger.error("根据{}获取不到WebSocket！", userName);
        }
    }

    @Override
    public void sendMessageByToken(String token, Object message) {
        WebSocket webSocket = this.get(token);
        if (webSocket != null) {
            WebSocketUtil.sendMessageAsync(webSocket.getSession(), message);
        } else {
            logger.error("根据[{}]获取的WebSocket为null!!", token);
        }

    }

    @Override
    public void broadcast(Object message) {
        localWebSocketMap().values().forEach(
                webSocket -> WebSocketUtil.sendMessageAsync(
                        webSocket.getSession(), message));
    }

    @Override
    public void onMessage(String token, String message) {
        WebSocket webSocket = connections.get(token);
        //发布一下消息事件,让关注该事件的人去处理
        if (null != webSocket) {
            getApplicationContext().publishEvent(new WebSocketEvent(webSocket, WebSocketEvent.EVENT_TYPE_MESSAGE, message));
        }
    }

    private List<WebSocket> getConnectionForUser(String userName) {
        Set<String> set = userName2Tokens.get(userName);
        List<WebSocket> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(set)) {
            set.forEach(token -> {
                WebSocket ws = connections.get(token);
                if (ws != null) {
                    list.add(ws);
                }
            });
        }
        return list;
    }

    protected void checkConnection() {
        Map<String, WebSocket> cons = localWebSocketMap();
        cons.forEach((identify, con) -> {
            if (System.currentTimeMillis() - con.getLastHeart().getTime() > 30000) {
                //timeout
                // remove(identify);
                logger.info(" websocket by time out:" + identify);
            }
        });
    }

    protected ConnectionTimeOutChecker startChecker() {
        ConnectionTimeOutChecker checker = new ConnectionTimeOutChecker();
        checker.start();
        return checker;
    }

    private class ConnectionTimeOutChecker extends Thread {
        @Override
        public void run() {
            try {
                sleep(20000);//20秒检查一次
                checkConnection();
            } catch (Exception ex) {
            }
        }
    }
}
