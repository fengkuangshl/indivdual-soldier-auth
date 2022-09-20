package com.key.win.file.util;

import com.key.win.basic.exception.BizException;
import com.key.win.basic.util.AccessPathUtils;
import com.key.win.basic.util.IndividualSoldierAuthConstantUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.util.Calendar;
import java.util.regex.Matcher;

@Component
public class FilePropertyUtils {
    private static Logger logger = LoggerFactory.getLogger(FilePropertyUtils.class);

    public static final String REDIS_CHUNK_FILE_COUNT_KEY_PREFIX = IndividualSoldierAuthConstantUtils.REDIS_ROOT_KEY_PREFIX + "chun:file:count:";

    private static Environment env;

    public static Environment getEnv() {
        return env;
    }

    @Autowired
    public void setEnv(Environment env) {
        this.env = env;
    }

    public static String getProperty(String property) {
        return env.getProperty(property);
    }

    public static String getPropertyByUploadBiz(String bizType) {
        return getProperty("spring.file.upload." + bizType + ".path");
    }

    public static String bizTypeCheck(@PathVariable String bizType) {
        if (StringUtils.isBlank(bizType)) {
            logger.error("上传文件业务类型为空！");
            throw new BizException("上传文件业务类型为空！");
        }
        String path = FilePropertyUtils.getPropertyByUploadBiz(bizType);
        if (StringUtils.isBlank(path)) {
            logger.error("上传文件业务路径不存在！");
            throw new BizException("上传文件业务路径不存在！");
        }
        return path;
    }



}
