package com.key.win.common.model.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.key.win.common.model.basic.MybatisID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@ApiModel("权限实体")
@Data
@TableName("sys_permission")
@EqualsAndHashCode(callSuper = true)
public class SysPermission extends MybatisID {
    @ApiModelProperty("权限")
    private String permission;
    @ApiModelProperty("名称")
    private String name;

}
