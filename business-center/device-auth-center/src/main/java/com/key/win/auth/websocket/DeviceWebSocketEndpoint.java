package com.key.win.auth.websocket;


import com.key.win.auth.util.DeviceAuthUtils;
import com.key.win.common.auth.AuthenticationUtil;
import com.key.win.websocket.WebSocket;
import com.key.win.websocket.endpoint.BaseWebSocketEndpoint;
import com.key.win.websocket.manager.WebSocketManager;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;

@Component
@ServerEndpoint(value = "/ws/device/{androidId}/{serialNumber}")
public class DeviceWebSocketEndpoint extends BaseWebSocketEndpoint {


    public void connect(Session session, String token) {
        if (authenticatorToken(session, "connect", token)) return;
        try {
            WebSocketManager websocketManager = getWebSocketManager();
            WebSocket webSocket = new WebSocket();
            webSocket.setToken(token);
            webSocket.setSession(session);
            webSocket.setUserName(token);
            webSocket.setLastHeart(new Date());
            websocketManager.put(token, webSocket);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("androidId") String androidId, @PathParam("serialNumber") String serialNumber, EndpointConfig config) {
        try {
            logger.info("*** WebSocket opened from sessionId: {} , androidId: {} ,serialNumber: {}", session.getId(), androidId, serialNumber);
            DeviceAuthUtils.setUniqueCodeForOnLine(androidId, serialNumber);
            connect(session, DeviceAuthUtils.getUniqueCode(androidId, serialNumber));
            DeviceAuthUtils.deviceOnLineNotifyAction(androidId, serialNumber);
        } catch (Exception ex) {
            logger.error("建立webSocket出错:" + ex.getMessage(), ex);
        }

    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("androidId") String androidId, @PathParam("serialNumber") String serialNumber) {
        logger.info("接收到的数据为：{} from sessionId: {} , androidId: {} ,serialNumber: {} ", message, session.getId(), androidId, serialNumber);
        DeviceAuthUtils.setUniqueCodeForOnLine(androidId, serialNumber);
        receiveMessage(message, session, DeviceAuthUtils.getUniqueCode(androidId, serialNumber));
    }

    @OnClose
    public void onClose(Session session, @PathParam("androidId") String androidId, @PathParam("serialNumber") String serialNumber) {
        logger.info("*** WebSocket closed from sessionId {}  androidId: {} ,serialNumber: {} ", session.getId(), androidId, serialNumber);
        deviceOffLine(androidId, serialNumber);
    }

    private void deviceOffLine(@PathParam("androidId") String androidId, @PathParam("serialNumber") String serialNumber) {
        disconnect(DeviceAuthUtils.getUniqueCode(androidId, serialNumber));
        try {
            DeviceAuthUtils.deviceOffLineNotifyAction(androidId, serialNumber);
            DeviceAuthUtils.deleteDeviceInfo(androidId, serialNumber);
        } catch (Exception e) {
            logger.error("deviceOffLineNotifyAction出错:" + e.getMessage(), e);
        }
    }

    @OnError
    public void onError(Throwable t, @PathParam("androidId") String androidId, @PathParam("serialNumber") String serialNumber) {
        logger.info("发生异常：androidId: {} ,serialNumber: {} ", androidId, serialNumber);
        logger.error(t.getMessage(), t);
        deviceOffLine(androidId, serialNumber);
    }

    public boolean authenticatorToken(Session session, String method, String uniqueCode) {
        return false;
    }

    /**
     * 把用户设备为一分钟等待期
     * 如果websocket在一分钟还连接不上来，redis中的用户将过期
     * 也就意味着用户需要重新登录了
     */
    public void disconnect(String token) {
        getWebSocketManager().remove(token);
    }

}
