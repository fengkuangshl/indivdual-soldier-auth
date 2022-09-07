package com.key.win.config;

import com.key.win.basic.util.AccessPathUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfigurer implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/" + AccessPathUtils.getRootPath().substring(AccessPathUtils.getRootPath().lastIndexOf("\\") + 1) + "/**")
                .addResourceLocations("file:" + AccessPathUtils.getRootPath() + "/");

    }
}
