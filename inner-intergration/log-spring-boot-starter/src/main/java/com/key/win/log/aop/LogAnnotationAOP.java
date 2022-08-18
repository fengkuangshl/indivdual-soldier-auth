package com.key.win.log.aop;

import com.key.win.basic.util.JsonUtils;
import com.key.win.log.annotation.LogAnnotation;
import com.key.win.log.model.SysLog;
import com.key.win.log.service.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.task.TaskExecutor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

@Aspect
@Order(-1) // 保证该AOP在@Transactional之前执行
public class LogAnnotationAOP {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired(required = false)
    private SysLogService logService;

    @Autowired
    private TaskExecutor taskExecutor;


    @Around("@annotation(la)")
    public Object logSave(ProceedingJoinPoint joinPoint, LogAnnotation la) throws Throwable {

        // 请求流水号
        String traceId = this.getRandom();
        // 记录开始时间
        long start = System.currentTimeMillis();
        // 获取方法参数
        String url = null;
        String httpMethod = null;
        Object result = null;
        List<Object> httpReqArgs = new ArrayList<Object>();
        SysLog sysLog = new SysLog();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        LogAnnotation logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(LogAnnotation.class);
        sysLog.setModule(logAnnotation.module() + ":" + methodSignature.getDeclaringTypeName() + "/"
                + methodSignature.getName());

        Object[] args = joinPoint.getArgs();// 参数值
        url = methodSignature.getDeclaringTypeName() + "/" + methodSignature.getName();
        String params = null;
        for (Object object : args) {
            if (object instanceof HttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) object;
                url = request.getRequestURI();
                httpMethod = request.getMethod();
            } else if (object instanceof HttpServletResponse) {
            } else {

                httpReqArgs.add(object);
            }
        }

        try {
            params = JsonUtils.toJsonNoException(httpReqArgs);
            sysLog.setParams(params);
            // 打印请求参数参数
            logger.info("开始请求，traceId={},  url={} , httpMethod={}, reqData={} ", traceId, url, httpMethod, params);
        } catch (Exception e) {
            logger.error("记录参数失败：{}", e.getMessage());
        }

        try {
            // 调用原来的方法
            result = joinPoint.proceed();
            sysLog.setFlag(Boolean.TRUE);
        } catch (Exception e) {
            sysLog.setFlag(Boolean.FALSE);
            sysLog.setRemark(e.getMessage());
            logger.error("请求报错，traceId={},  url={} , httpMethod={}, reqData={} ,error ={} ", traceId, url, httpMethod, params, e.getMessage());
            throw e;
        } finally {
            //如果需要记录数据库开启异步操作
            if (logAnnotation.recordRequestParam()) {
                CompletableFuture.runAsync(() -> {
                    try {
                        logger.trace("日志落库开始：{}", sysLog);
                        if (logService != null) {
                            logService.saveLog(sysLog);
                        }
                        logger.trace("开始落库结束：{}", sysLog);
                    } catch (Exception e) {
                        logger.error("落库失败：{}", e.getMessage());
                    }

                }, taskExecutor);
            }

            // 获取回执报文及耗时
            logger.info("请求完成, traceId={}, 耗时={}, resp={}:", traceId, (System.currentTimeMillis() - start),
                    result == null ? null : JsonUtils.toJsonNoException(result));

        }
        return result;
    }


    /**
     * 生成日志随机数
     *
     * @return
     */
    public String getRandom() {
        int i = 0;
        StringBuilder st = new StringBuilder();
        while (i < 5) {
            i++;
            st.append(ThreadLocalRandom.current().nextInt(10));
        }
        return st.toString() + System.currentTimeMillis();
    }

}