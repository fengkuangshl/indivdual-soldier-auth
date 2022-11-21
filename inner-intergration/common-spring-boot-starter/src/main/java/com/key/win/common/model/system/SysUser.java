package com.key.win.common.model.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.key.win.common.enums.SexEnum;
import com.key.win.common.enums.UserTypeEnum;
import com.key.win.common.model.basic.MybatisID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@ApiModel("用户实体")
@Data
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
public class SysUser extends MybatisID {
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("状态：true-启用，false-禁用")
    private Boolean enabled = Boolean.TRUE;
    @ApiModelProperty("性别:[男:0, 女:1],默认：0")
    private SexEnum sex = SexEnum.MALE;
    @ApiModelProperty("手机号码")
    private String phone;
    @ApiModelProperty("用户头像")
    private String headImgUrl;
    @ApiModelProperty("用户类型[普通用户：0,管理员:1]，默认:0")
    private UserTypeEnum type = UserTypeEnum.NORMAL;
    @ApiModelProperty("用户签名")
    private String sign;
    @ApiModelProperty("email")
    private String email;


    @ApiModelProperty("角色Id")
    @TableField(exist = false)
    private Long roleId;
    @ApiModelProperty("新密码")
    @TableField(exist = false)
    private String newPassword;
    @ApiModelProperty("组Id")
    @TableField(exist = false)
    private Long groupId;
    @ApiModelProperty("角色列表")
    @TableField(exist = false)
    private List<SysRole> sysRoles;
    @ApiModelProperty("组列表")
    @TableField(exist = false)
    private List<SysGroup> sysGroups;
    @ApiModelProperty("用户Id集合")
    @TableField(exist = false)
    private Set<Long> userIds;
    @ApiModelProperty("组Id集合")
    @TableField(exist = false)
    private Set<Long> groupIds;
    @ApiModelProperty("角色Id集合")
    @TableField(exist = false)
    private Set<Long> roleIds;


    @ApiModelProperty("在否在线：true-在线，false-下线")
    @TableField(exist = false)
    private boolean isOnLine;

    @ApiModelProperty("token")
    @TableField(exist = false)
    private String token;

    @ApiModelProperty("refreshToken")
    @TableField(exist = false)
    private String refreshToken;


    @JsonIgnore
    public String getAvatar() {
        return this.headImgUrl;
    }

}
