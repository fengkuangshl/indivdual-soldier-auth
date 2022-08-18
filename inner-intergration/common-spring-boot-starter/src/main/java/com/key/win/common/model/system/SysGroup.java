package com.key.win.common.model.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.key.win.common.model.basic.MybatisID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ApiModel("组实体")
@Data
@TableName("sys_group")
@EqualsAndHashCode(callSuper = true)
public class SysGroup extends MybatisID {
    @ApiModelProperty("组code")
    private String code;
    @ApiModelProperty("组名称")
    private String name;
    @ApiModelProperty("父节点Id")
    private Long parentId;
    @ApiModelProperty("用户Id")
    @TableField(exist = false)
    private Long userId;
    @ApiModelProperty("用户列表")
    @TableField(exist = false)
    private List<SysUser> sysUsers;
    @ApiModelProperty("用户Id列表")
    @TableField(exist = false)
    private Set<Long> userIds;
    @ApiModelProperty("孩子节点")
    @TableField(exist = false)
    private List<SysGroup> subGroup = new ArrayList<>();

    public void addSubGroup(SysGroup sysOrgan) {
        this.subGroup.add(sysOrgan);
    }
}
