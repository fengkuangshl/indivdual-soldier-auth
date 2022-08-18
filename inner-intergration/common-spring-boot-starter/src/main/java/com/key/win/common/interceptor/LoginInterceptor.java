package com.key.win.common.interceptor;

import com.key.win.basic.exception.UserIllegalException;
import com.key.win.basic.util.IndivdualSoldierAuthConstantUtils;
import com.key.win.common.auth.AuthenticationUtil;
import com.key.win.common.auth.detail.Authentication;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

public class LoginInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = this.extractToken(request);
        if (StringUtils.isBlank(token)) {
            throw new UserIllegalException("token缺失！");
        }
        logger.info("获取用户token：{}", token);
        Authentication user = AuthenticationUtil.getAuthenticationToRedis(token);
        if (user == null) {
            throw new UserIllegalException("用户不存在");
        }
        AuthenticationUtil.setCurrentUser(user);
        AuthenticationUtil.setAuthenticationTokenExpires(token);
        return true;
    }

    protected String extractToken(HttpServletRequest request) {
        String token = this.extractHeaderToken(request);
        if (token == null) {
            logger.warn("Token not found in headers. Trying request parameters.");
            token = request.getParameter(IndivdualSoldierAuthConstantUtils.REQUEST_TOKEN_KEY);
            if (token == null) {
                logger.error("Token not found in request parameters.  illegal request.");
            }
        }
        return token;
    }

    protected String extractHeaderToken(HttpServletRequest request) {
        Enumeration headers = request.getHeaders(IndivdualSoldierAuthConstantUtils.REQUEST_HEADER_AUTHORIZATION);
        String value;
        do {
            if (!headers.hasMoreElements()) {
                return null;
            }

            value = (String) headers.nextElement();
        } while (!value.toLowerCase().startsWith(IndivdualSoldierAuthConstantUtils.TOKEN_BEARER_VAUE.toLowerCase()));

        String authHeaderValue = value.substring(IndivdualSoldierAuthConstantUtils.TOKEN_BEARER_VAUE.length()).trim();
//        int commaIndex = authHeaderValue.indexOf(44);
//        if (commaIndex > 0) {
//            authHeaderValue = authHeaderValue.substring(0, commaIndex);
//        }

        return authHeaderValue;
    }
}
