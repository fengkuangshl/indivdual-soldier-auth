package com.key.win.datalog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataLog {
    /**
     * spring el表达式1
     */
    String spel() default "";


    /**
     * <p>
     * 类型
     * </p>
     */
    int type() default -1;

    /**
     * <p>
     * 标签
     * </p>
     */
    String tag() default "";

    /**
     * <p>
     * 注释
     * </p>
     */
    String note() default "";
}
