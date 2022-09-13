package com.key.win.auth.websocket;


import com.key.win.auth.util.DeviceAuthUtils;
import com.key.win.websocket.endpoint.BaseWebSocketEndpoint;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint(value = "/ws/device/{androidId}/{serialNumber}")
public class DeviceWebSocketEndpoint extends BaseWebSocketEndpoint {

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
        disconnect(DeviceAuthUtils.getUniqueCode(androidId, serialNumber));
        try {
            DeviceAuthUtils.deviceOffLineNotifyAction(androidId, serialNumber);
        } catch (Exception e) {
            logger.error("deviceOffLineNotifyAction出错:" + e.getMessage(), e);
        }
    }

    @OnError
    public void onError(Throwable t, @PathParam("androidId") String androidId, @PathParam("serialNumber") String serialNumber) {
        logger.info("发生异常：androidId: {} ,serialNumber: {} ", androidId, serialNumber);
        logger.error(t.getMessage(), t);
        disconnect(DeviceAuthUtils.getUniqueCode(androidId, serialNumber));
        try {
            DeviceAuthUtils.deviceOffLineNotifyAction(androidId, serialNumber);
        } catch (Exception e) {
            logger.error("deviceOffLineNotifyAction出错:" + e.getMessage(), e);
        }
    }

    public boolean authenticatorToken(Session session, String method, String uniqueCode) {
        return false;
    }

}
