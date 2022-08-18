package com.key.win.file.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.regex.Matcher;

@Component
public class FilePropertyUtils {

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


}
