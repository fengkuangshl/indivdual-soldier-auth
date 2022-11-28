package com.key.win.common.config;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * AOP事务处理
 * <p>
 * spring事务处理优先使用事务配置顺序
 * 方法级别@Transactional -> 类级别@Transactional -> AOP配置
 */
@Aspect
@Configuration
@ConditionalOnProperty(name = "spring.tx.manager.enabled", matchIfMissing = false, havingValue = "true")
public class TxAdviceConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * service事务aop
     */
    // module.tx.service.enabled
    public static String TX_SERVICE_ENABLED = "true";
    public static String TX_SERVICE_POINTCUT_EXPRESSION = "execution (* com.key.win..service.impl.*Impl.*(..))";
    public static int TX_SERVICE_METHOD_TIMEOUT = 5;

    @Value("${spring.tx.service.pointcut.expression}")
    public void setTX_SERVICE_POINTCUT_EXPRESSION(String TX_SERVICE_POINTCUT_EXPRESSION) {
        TxAdviceConfig.TX_SERVICE_POINTCUT_EXPRESSION = TX_SERVICE_POINTCUT_EXPRESSION;
    }

    @Value("${spring.tx.service.method.timeout:5}")
    public void setTX_SERVICE_METHOD_TIMEOUT(int TX_SERVICE_METHOD_TIMEOUT) {
        TxAdviceConfig.TX_SERVICE_METHOD_TIMEOUT = TX_SERVICE_METHOD_TIMEOUT;
    }

    /**
     * 配置事务管理器使用springboot默认的
     *
     * 关于事务管理器，不管是JPA还是JDBC等都实现自接口 PlatformTransactionManager
     * 如果你添加的是 spring-boot-starter-jdbc 依赖，框架会默认注入 DataSourceTransactionManager 实例。
     * 如果你添加的是 spring-boot-starter-data-jpa 依赖，框架会默认注入 JpaTransactionManager 实例。
     *
     * 手工注解@Bean 将被优先加载，框架不会重新实例化其他的 PlatformTransactionManager 实现类。
     * @param dataSource
     * @return
     * @throws Exception
     */
    /*@Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }*/

    /**
     * 事务的实现Advice
     *
     * @return
     * @see org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration$TransactionTemplateConfiguration
     */
    @Bean
    public TransactionInterceptor txAdvice(/*@Qualifier("txManager") */TransactionManager transactionManager) {
        logger.warn("#[Tx Config:TX_SERVICE_METHOD_TIMEOUT]" + TX_SERVICE_METHOD_TIMEOUT);

        /* 只读事务，不做更新操作， 不超时 */
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setName("PTA-READONLY-TX");
        readOnlyTx.setDescriptor("PTA READONLY TX");
        readOnlyTx.setReadOnly(true);
        //readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);

        /* 当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务 */
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        requiredTx.setName("PTA-REQUIRED-TX");
        requiredTx.setDescriptor("PTA REQUIRED TX");
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        requiredTx.setTimeout(TX_SERVICE_METHOD_TIMEOUT);

        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("login*", readOnlyTx);
        txMap.put("get*", readOnlyTx);
        txMap.put("query*", readOnlyTx);
        txMap.put("find*", readOnlyTx);
        txMap.put("select*", readOnlyTx);
        txMap.put("qry*", readOnlyTx);
        txMap.put("add*", requiredTx);
        txMap.put("save*", requiredTx);
        txMap.put("insert*", requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("delete*", requiredTx);
        txMap.put("*", requiredTx);

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.setNameMap(txMap);
        TransactionInterceptor txAdvice = new TransactionInterceptor(transactionManager, source);
        return txAdvice;
    }

    /**
     * 切面的定义    pointcut及advice
     *
     * @param txAdvice
     * @return
     */
    @Bean
    public Advisor txAdviceAdvisor(/*@Qualifier("txAdvice")*/ TransactionInterceptor txAdvice) {
        logger.warn("#[Tx Config:TX_SERVICE_POINTCUT_EXPRESSION]" + TX_SERVICE_POINTCUT_EXPRESSION);

            AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
            pointcut.setExpression(TX_SERVICE_POINTCUT_EXPRESSION);
            return new DefaultPointcutAdvisor(pointcut, txAdvice);
//        AspectJExpressionPointcutAdvisor aspectJExpressionPointcutAdvisor = new AspectJExpressionPointcutAdvisor();
//        aspectJExpressionPointcutAdvisor.setExpression(TX_SERVICE_POINTCUT_EXPRESSION);
//        aspectJExpressionPointcutAdvisor.setAdvice(txAdvice);
//        return aspectJExpressionPointcutAdvisor;

    }

}