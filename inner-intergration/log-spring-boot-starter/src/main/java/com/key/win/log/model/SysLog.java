package com.key.win.log.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.key.win.common.model.basic.MybatisID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_log")
@EqualsAndHashCode(callSuper = true)
@ApiModel("日志实体")
public class SysLog extends MybatisID {

    @ApiModelProperty("归属模块")
    private String module;    //	归属模块
    @ApiModelProperty("执行方法的参数值")
    private String params;    //	执行方法的参数值
    @ApiModelProperty("备注")
    private String remark;  //  备注
    @ApiModelProperty("是否执行成功")
    private Boolean flag;    //	是否执行成功
    @TableField(exist = false)
    @ApiModelProperty("查询的字段")
    private String searchContent;
}
