package com.key.win.log.selector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;


public class SysLogImportSelector implements ImportSelector {
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        logger.info("系统日志已启动！！");
        return new String[]{
                "com.key.win.common.log.aop.LogAnnotationAOP"

        };
    }

}