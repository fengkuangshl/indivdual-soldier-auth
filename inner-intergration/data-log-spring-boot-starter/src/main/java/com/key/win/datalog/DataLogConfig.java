package com.key.win.datalog;

import com.key.win.datalog.annotation.EnableDataLog;
import com.key.win.datalog.handle.BaseDataLog;
import com.key.win.datalog.interceptor.DataUpdateInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDataLog
public class DataLogConfig {
    /**
     * <p>
     * 数据更新操作处理
     * </p>
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(BaseDataLog.class)
    public DataUpdateInterceptor dataUpdateInterceptor() {
        return new DataUpdateInterceptor();
    }
}
