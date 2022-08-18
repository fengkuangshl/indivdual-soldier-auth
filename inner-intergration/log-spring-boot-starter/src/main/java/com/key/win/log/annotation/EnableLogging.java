package com.key.win.log.annotation;

import com.key.win.log.selector.SysLogImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SysLogImportSelector.class)
public @interface EnableLogging{
//	String name() ;
}