package com.key.win.basic.util;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogIpConfig extends ClassicConverter {
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static String webIP;

    static {
        webIP = IPUtils.getHostIp();
    }

    @Override
    public String convert(ILoggingEvent event) {
        return webIP;
    }
}