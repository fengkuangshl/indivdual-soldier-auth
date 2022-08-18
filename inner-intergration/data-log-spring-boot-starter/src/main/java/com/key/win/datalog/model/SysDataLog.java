package com.key.win.datalog.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.key.win.common.model.basic.MybatisID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 数据日志
 * </p>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_data_Log")
@ApiModel("数据日志实体")
public class SysDataLog extends MybatisID {
    @ApiModelProperty("数据日志记录")
    private String content;
    @ApiModelProperty("外键Id[这个只是一个软连接，没有实质建议数据库的外键关系]")
    private String fkId;

    @TableField(exist = false)
    @ApiModelProperty("查询的字段")
    private String searchContent;
}
