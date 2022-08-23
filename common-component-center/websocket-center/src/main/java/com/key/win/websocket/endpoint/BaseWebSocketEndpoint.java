package com.key.win.websocket.endpoint;

import com.key.win.basic.util.IndivdualSoldierAuthConstantUtils;
import com.key.win.basic.util.SpringUtils;
import com.key.win.common.auth.AuthenticationUtil;
import com.key.win.websocket.WebSocket;
import com.key.win.websocket.manager.WebSocketManager;
import com.key.win.websocket.utils.WebSocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Date;


public abstract class BaseWebSocketEndpoint {
    /**
     * 路径标识：目前使用token来代表
     */
    public static final String TOKEN = IndivdualSoldierAuthConstantUtils.TOKEN;
    protected static final Logger logger = LoggerFactory.getLogger(BaseWebSocketEndpoint.class);

    public void connect(Session session, String token) {
        if (redisAuthentiacationCheck(session, "connect", token)) return;
        try {
            WebSocketManager websocketManager = getWebSocketManager();
            WebSocket webSocket = new WebSocket();
            webSocket.setToken(token);
            webSocket.setSession(session);
            webSocket.setUserName(AuthenticationUtil.getAuthenticationToRedis(token).getUserName());
            webSocket.setLastHeart(new Date());
            websocketManager.put(AuthenticationUtil.getToken(), webSocket);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 把用户设备为一分钟等待期
     * 如果webscoket在一分钟还连接不上来，redis中的用户将过期
     * 也就意味着用户需要重新登录了
     */
    public void disconnect(String token) {
        getWebSocketManager().remove(token);
        if (AuthenticationUtil.getAuthenticationToRedis(token) != null) {
            AuthenticationUtil.deleteTokenToRedis(token);
            logger.info("websocket关闭时，用户信息立刻失效！！");
        } else {
            logger.info("说明用户信息已经失效了！");
        }
    }

    public void receiveMessage(String message, Session session, String token) {
        if (redisAuthentiacationCheck(session, "receiveMessage", token)) return;
        WebSocketManager webSocketManager = getWebSocketManager();
        //心跳监测
        if (webSocketManager.isPing(AuthenticationUtil.getToken(), message)) {
            String pong = webSocketManager.pong(token, message);
            WebSocketUtil.sendMessageAsync(session, pong);
            WebSocket webSocket = webSocketManager.get(token);
            //更新心跳时间
            if (null != webSocket) {
                webSocket.setLastHeart(new Date());
            }
            return;
        }
        //收到其他消息的时候
        webSocketManager.onMessage(token, message);
    }

    private boolean redisAuthentiacationCheck(Session session, String method, String token) {
        if (AuthenticationUtil.getAuthenticationToRedis(token) == null) {
            try {
                session.close();
                this.disconnect(token);
                logger.info("websocket {}时，token为空时关闭websocket成功！", method);
                return true;
            } catch (IOException e) {
                logger.error("websocket {}时，token为空时关闭websocket时出错：{}", method, e.getMessage(), e);
            }
        }
        return false;
    }

    protected WebSocketManager getWebSocketManager() {
        return SpringUtils.getBean(WebSocketManager.WEBSOCKET_MANAGER_NAME);
    }
}
