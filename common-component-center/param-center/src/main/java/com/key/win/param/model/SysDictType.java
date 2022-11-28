
package com.key.win.param.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.key.win.common.model.basic.MybatisID;
import com.key.win.common.model.basic.MybatisIDForAssignID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.key.win.param.enums.SysDictTypeEnum;

@Data
@TableName("sys_dic_type")
@EqualsAndHashCode(callSuper = true)
@ApiModel("字典类型")
public class SysDictType extends MybatisIDForAssignID {


    /**
     * 字典名称
     **/
    @ApiModelProperty("字典名称")
    private String name;

    /**
     * 字典类型
     **/
    @ApiModelProperty("字典code")
    private String code;

    /**
     * 字典类型
     **/
    @ApiModelProperty("字典类型")
    private SysDictTypeEnum type;

    /**
     * 状态（0正常 1停用）
     **/
    @ApiModelProperty("状态")
    private boolean status;

    /**
     * 备注
     **/
    @ApiModelProperty("备注")
    private String remark;

}