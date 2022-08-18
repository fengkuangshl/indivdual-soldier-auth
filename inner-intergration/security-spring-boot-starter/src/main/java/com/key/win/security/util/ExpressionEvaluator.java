package com.key.win.security.util;

import com.key.win.basic.util.SpringUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ExpressionEvaluator extends CachedExpressionEvaluator {

    private final ParameterNameDiscoverer paramNameDiscoverer = new DefaultParameterNameDiscoverer();


    private final Map conditionCache = new ConcurrentHashMap<>(64);


    private final Map targetMethodCache = new ConcurrentHashMap<>(64);


    public EvaluationContext createEvaluationContext(Object object, Class targetClass, Method method, Object[] args) {
        Method targetMethod = getTargetMethod(targetClass, method);
        ExpressionRootObject root = new ExpressionRootObject(object, args);
        MethodBasedEvaluationContext methodBasedEvaluationContext = new MethodBasedEvaluationContext(root, targetMethod, args, this.paramNameDiscoverer);
        ApplicationContext ctx = SpringUtils.getApplicationContext();
        methodBasedEvaluationContext.setBeanResolver(new BeanFactoryResolver(ctx));
        return methodBasedEvaluationContext;

    }


    public Object key(String conditionExpression, AnnotatedElementKey elementKey, EvaluationContext evalContext) {
        return this.getExpression(this.conditionCache, elementKey, conditionExpression).getValue(evalContext);

    }


    private Method getTargetMethod(Class targetClass, Method method) {
        AnnotatedElementKey methodKey = new AnnotatedElementKey(method, targetClass);
        Method targetMethod = (Method) this.targetMethodCache.get(methodKey);
        if (targetMethod == null) {
            targetMethod = AopUtils.getMostSpecificMethod(method, targetClass);
            if (targetMethod == null) {
                targetMethod = method;
            }
            this.targetMethodCache.put(methodKey, targetMethod);
        }
        return targetMethod;
    }

}