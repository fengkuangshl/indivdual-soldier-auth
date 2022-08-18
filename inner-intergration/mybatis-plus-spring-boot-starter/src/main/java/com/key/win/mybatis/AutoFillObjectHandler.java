package com.key.win.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.key.win.common.auth.AuthenticationUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * 自动填充类，如果是采用mybatis作为数据库持久操作层，则会采用该拦截器进行公共自动填充 如果是匿名的方式登录，即：无法获取当前认证信息的，会将
 * 创建人/更新人 赋值为 匿名用户 即:ANONYMOUS
 */
@Configuration
public class AutoFillObjectHandler implements MetaObjectHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AutoFillObjectHandler() {
        logger.info("Mybatis 自动填充装置已启动....");
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        logger.info("mybatis数据持久....添加自动填充....");
        this.strictInsertFill(metaObject, "createDate", Date.class, new Date());
        this.strictInsertFill(metaObject, "createUserId", Long.class, AuthenticationUtil.getUserId());
        this.strictInsertFill(metaObject, "createUserName", String.class, AuthenticationUtil.getUserName());
        setFieldValByName("version", 0, metaObject);
        logger.info("mybatis数据持久....填充完毕....");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        logger.info("mybatis数据持久....更新前自动填充....");
        this.strictUpdateFill(metaObject, "updateDate", Date.class, new Date());
        this.strictUpdateFill(metaObject, "updateUserId", Long.class, AuthenticationUtil.getUserId());
        this.strictUpdateFill(metaObject, "updateUserName", String.class, AuthenticationUtil.getUserName());
        logger.info("mybatis数据持久....更新填充完毕....");
    }

}