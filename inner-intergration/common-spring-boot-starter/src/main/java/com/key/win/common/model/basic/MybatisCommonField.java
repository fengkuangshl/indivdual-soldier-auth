package com.key.win.common.model.basic;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class MybatisCommonField extends MybatisVersion {
    /**
     *
     */
    private static final long serialVersionUID = 3803687288306105240L;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createDate = new Date();
    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateDate = new Date();
    @ApiModelProperty("创建人Id")
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;
    @ApiModelProperty("更新人Id")
    @TableField(fill = FieldFill.UPDATE)
    private Long updateUserId;
    @ApiModelProperty("逻辑删除：1-正常,0-删除")
    @TableLogic(value = "1", delval = "0")
    private Boolean enableFlag = Boolean.TRUE;
    @ApiModelProperty("创建用户名称")
    @TableField(fill = FieldFill.INSERT)
    private String createUserName;
    @ApiModelProperty("更新用户名称")
    @TableField(fill = FieldFill.UPDATE)
    private String updateUserName;

}