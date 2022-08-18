package com.key.win.basic.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.regex.Matcher;

@Component
public class AccessPathUtils {

    private static String accessPathPrefix;

    private static String rootPath;


    private static Environment env;

    public static Environment getEnv() {
        return env;
    }

    @Autowired
    public void setEnv(Environment env) {
        this.env = env;
    }

    public static String getAccessPathPrefix() {
        if(StringUtils.isNotBlank(accessPathPrefix)){
            if (accessPathPrefix.endsWith("/")) {
                accessPathPrefix = accessPathPrefix.substring(0, accessPathPrefix.length() - 1);
            }
        }
        return accessPathPrefix;
    }

    @Value("${spring.file.access-path-prefix}")
    public void setAccessPathPrefix(String accessPathPrefix) {
        AccessPathUtils.accessPathPrefix = accessPathPrefix;
    }

    public static String getRootPath() {
        return rootPath;
    }

    @Value("${spring.file.root-path}")
    public void setRootPath(String rootPath) {
        this.rootPath = StringUtils.isNotBlank(rootPath) ? rootPath.replaceAll("/", Matcher.quoteReplacement(File.separator)) : null;
    }

    public static String getProperty(String property) {
        return env.getProperty(property);
    }

}
