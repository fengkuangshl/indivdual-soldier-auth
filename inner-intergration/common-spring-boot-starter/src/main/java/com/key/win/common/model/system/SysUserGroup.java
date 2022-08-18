package com.key.win.common.model.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.key.win.common.model.basic.MybatisID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel("用户组色实体")
@Data
@TableName("sys_user_group")
@EqualsAndHashCode(callSuper = true)
public class SysUserGroup extends MybatisID {
    @ApiModelProperty("用户Id")
    private Long userId;
    @ApiModelProperty("组Id")
    private Long groupId;

}
