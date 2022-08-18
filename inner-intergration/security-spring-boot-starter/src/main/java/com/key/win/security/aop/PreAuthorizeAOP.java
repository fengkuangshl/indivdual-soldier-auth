package com.key.win.security.aop;

import com.key.win.basic.exception.AccessDeniedException;
import com.key.win.security.annotation.PreAuthorize;
import com.key.win.security.util.AspectSupportUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@ConditionalOnProperty(name = "spring.global.method.security.enable", matchIfMissing = false, havingValue = "true")
public class PreAuthorizeAOP {

    private  final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Before("@annotation(perAuthrize)")
    public void permissionProcess(JoinPoint point, PreAuthorize perAuthrize) throws Throwable {
        String value = perAuthrize.value();
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        logger.info("对{}进行权限校验{}", method, value);
        boolean b = (boolean) AspectSupportUtils.getKeyValue(point, value);
        if (!b) {
            logger.error("对{}进行权限校验{}结果:不通过！", method, value);
            throw new AccessDeniedException("权限不足");
        } else {
            logger.info("对{}进行权限校验{}结果:通过！", method, value);
        }

    }
}
