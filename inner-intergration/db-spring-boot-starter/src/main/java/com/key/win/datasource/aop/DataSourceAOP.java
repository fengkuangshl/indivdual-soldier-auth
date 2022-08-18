package com.key.win.datasource.aop;


import com.key.win.datasource.annotation.DataSource;
import com.key.win.datasource.constant.DataSourceKey;
import com.key.win.datasource.util.DataSourceHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

@Aspect
@Order(-1) // 保证该AOP在@Transactional之前执行
public class DataSourceAOP {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("@within(ds)")
    public void changeDataSourceClass(JoinPoint point, DataSource ds) throws Throwable {
        changeDataSource(point, ds);
    }

    private void changeDataSource(JoinPoint point, DataSource ds) {
        String dsId = ds.name();
        try {
            DataSourceKey dataSourceKey = DataSourceKey.valueOf(dsId);
            DataSourceHolder.setDataSourceKey(dataSourceKey);
        } catch (Exception e) {
            logger.error("数据源[{}]不存在，使用默认数据源 > {}", ds.name(), point.getSignature());
        }
    }

    @Before("@annotation(ds)")
    public void changeDataSourceMethod(JoinPoint point, DataSource ds) throws Throwable {
        changeDataSource(point, ds);
    }

    @After("@within(ds)")
    public void restoreDataSourceClass(JoinPoint point, DataSource ds) {
        restroeDataSource(point, ds);
    }

    private void restroeDataSource(JoinPoint point, DataSource ds) {
        logger.debug("Revert DataSource : {transIdo} > {}", ds.name(), point.getSignature());
        DataSourceHolder.clearDataSourceKey();
    }

    @After("@annotation(ds)")
    public void restoreDataSourceMethod(JoinPoint point, DataSource ds) {
        restroeDataSource(point, ds);
    }

}