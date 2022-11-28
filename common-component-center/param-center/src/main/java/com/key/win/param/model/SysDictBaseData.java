package com.key.win.param.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.key.win.common.model.basic.MybatisID;
import com.key.win.common.model.basic.MybatisIDForAssignID;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class SysDictBaseData extends MybatisIDForAssignID {

    /**
     * 字典排序
     */
    @ApiModelProperty("字典排序")
    private Integer sort;

    /**
     * 字典标签
     */
    @ApiModelProperty("字典标签")
    private String label;

    /**
     * 字典键值
     */
    @ApiModelProperty("字典键值")
    private String value;

    /**
     * 状态（0正常 1停用）
     */
    @ApiModelProperty("状态（Y正常 N停用）")
    private Boolean status;

    /**
     * 备注
     **/
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 字典类型
     */
    @ApiModelProperty("字典类型")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long type;
    @ApiModelProperty("扩展属性1")
    private String attr1;
    @ApiModelProperty("扩展属性2")
    private String attr2;
    @ApiModelProperty("扩展属性3")
    private String attr3;
    @ApiModelProperty("扩展属性4")
    private String attr4;
    @ApiModelProperty("扩展属性5")
    private String attr5;

}