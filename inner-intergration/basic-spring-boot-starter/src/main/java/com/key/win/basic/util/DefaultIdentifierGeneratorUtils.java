package com.key.win.basic.util;

import com.key.win.basic.sequence.Sequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultIdentifierGeneratorUtils {

    private static Logger logger = LoggerFactory.getLogger(DefaultIdentifierGeneratorUtils.class);

    private final static Sequence sequence = new Sequence();

    public static Long getGeneratorLongId() {
        return sequence.nextId();
    }

    public static void main(String[] args) {
        System.out.println(DefaultIdentifierGeneratorUtils.getGeneratorLongId());
        System.out.println(DefaultIdentifierGeneratorUtils.getGeneratorLongId());
        System.out.println(DefaultIdentifierGeneratorUtils.getGeneratorLongId());
        System.out.println(DefaultIdentifierGeneratorUtils.getGeneratorLongId());
    }
}
