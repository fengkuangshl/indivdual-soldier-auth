package com.key.win.rsa.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.key.win.rsa.filter.ParameterFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "spring.rsa", name = "intercept-url-path")
public class CustomEncryptConfiguration {

    @Value("${spring.rsa.intercept-url-path}")
    private String interceptUrlPath;
    @Bean
    public FilterRegistrationBean<ParameterFilter> customEncryptFilter(ObjectMapper objectMapper) {
        FilterRegistrationBean<ParameterFilter> bean = new FilterRegistrationBean<>(new ParameterFilter(objectMapper));
        bean.addUrlPatterns(interceptUrlPath);
        return bean;
    }
}