package com.key.win.websocket.config;


import com.key.win.basic.exception.UserIllegalException;
import com.key.win.basic.util.IndivdualSoldierAuthConstantUtils;
import com.key.win.basic.util.SpringUtils;
import com.key.win.common.auth.AuthenticationUtil;
import com.key.win.common.auth.detail.Authentication;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.HashMap;
import java.util.Map;


public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {

    private  final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String SPRING_WEB_SOCKET_PATH = "spring.web.socket.path";

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
//        HttpSession httpSession=(HttpSession) request.getHttpSession();
//        sec.getUserProperties().put(HttpSession.class.getName(),httpSession);
        getAuthentication(request);
        super.modifyHandshake(sec, request, response);
    }

    private void getAuthentication(HandshakeRequest request) {
        String webSocketPath = SpringUtils.getProperty(SPRING_WEB_SOCKET_PATH);
        Map<String, String> pathVariableMap = this.matchPath(request.getRequestURI().getPath(), webSocketPath.split(","));
        String token = pathVariableMap.get(IndivdualSoldierAuthConstantUtils.TOKEN);
        if (StringUtils.isBlank(token)) {
            throw new UserIllegalException("token缺失！");
        }
        Authentication user = AuthenticationUtil.getAuthenticationToRedis(token);
        if (user == null) {
            throw new UserIllegalException("用户不存在");
        }
        AuthenticationUtil.setCurrentUser(user);
        //AuthenticationUtil.setAuthenticationExpire(token);
    }

    private Map<String, String> matchPath(String path, String[] webSocketPaths) {
        logger.info("用户请求路径:{}", path);
        Map<String, String> pathVariableMap = new HashMap<>();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        if (webSocketPaths != null && webSocketPaths.length > 0) {
            String matchPath = "";
            for (String webSocketPath : webSocketPaths) {
                boolean b = antPathMatcher.match(webSocketPath, path);
                if (b) {
                    logger.info("匹配到的路径:{}", webSocketPath);
                    matchPath = webSocketPath;
                    break;
                }
            }
            if (StringUtils.isNotBlank(matchPath)) {
                String[] matchPaths = matchPath.split("/");
                String[] paths = path.split("/");
                for (int i = 0; i < matchPaths.length; i++) {
                    String p = matchPaths[i];
                    if (p.startsWith("{") && p.endsWith("}")) {
                        logger.info("匹配到的值{}:{}", p, paths[i]);
                        pathVariableMap.put(p.substring(1, p.length() - 1), paths[i]);
                    }
                }
            }
        }
        return pathVariableMap;
    }
}
