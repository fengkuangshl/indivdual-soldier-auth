package com.key.win.security.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;


public class AspectSupportUtils {

    private final static String DEFAULT_VERIFY_BEAN_ID = "rbacService";
    private final static String DEFAULT_KEY_EXPRESSION_PREFIX = "@" + DEFAULT_VERIFY_BEAN_ID + ".";
    private static ExpressionEvaluator evaluator = new ExpressionEvaluator();


    public static Object getKeyValue(JoinPoint joinPoint, String keyExpression) {
        return getKeyValue(joinPoint.getTarget(), joinPoint.getArgs(), joinPoint.getTarget().getClass(),
                ((MethodSignature) joinPoint.getSignature()).getMethod(), keyExpression);

    }


    public static Object getKeyValue(Object object, Object[] args, Class clazz, Method method,
                                      String keyExpression) {
        if (StringUtils.hasText(keyExpression)) {
            EvaluationContext evaluationContext = evaluator.createEvaluationContext(object, clazz, method, args);
            AnnotatedElementKey methodKey = new AnnotatedElementKey(method, clazz);
            return evaluator.key(processKeyExpression(keyExpression), methodKey, evaluationContext);
        }
        return SimpleKeyGenerator.generateKey(args);
    }

    private static String processKeyExpression(String keyExpression) {
        if (!keyExpression.startsWith("@")) {
            return DEFAULT_KEY_EXPRESSION_PREFIX + keyExpression;
        }
        return keyExpression;
    }

}