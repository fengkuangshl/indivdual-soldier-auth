package com.key.win.datalog.vo;

import lombok.Data;

@Data
public class CompareResultVo {
    /**
     * 主键id值
     */
    private Long id;
    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 字段注释
     */
    private String fieldComment;
    /**
     * 字段旧值
     */
    private Object oldValue;
    /**
     * 字段新值
     */
    private Object newValue;
}
