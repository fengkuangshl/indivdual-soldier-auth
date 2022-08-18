package com.key.win.datalog.annotation;

import com.key.win.datalog.selector.SysDataLogImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SysDataLogImportSelector.class)
public @interface EnableDataLog {
//	String name() ;
}