package com.key.win.datalog.selector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;


public class SysDataLogImportSelector implements ImportSelector {
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        logger.info("数据日志已启动！");
        return new String[]{
                "com.key.win.datalog.aop.SysDataLogAop",
                "com.key.win.datalog.handle.SysDataLogHandle"
        };
    }

}