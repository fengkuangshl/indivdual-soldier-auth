package com.key.win.param.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@ApiModel("字典实体")
@Data
@TableName("sys_dict_tree")
@EqualsAndHashCode(callSuper = true)
public class SysDictTree extends SysDictBaseData {

    @ApiModelProperty("父节点Id")
    private Long parentId;

    @ApiModelProperty("级联")
    private String cascadeCode;

    @ApiModelProperty("孩子节点")
    @TableField(exist = false)
    private List<SysDictTree> subDictTree = new ArrayList<>();


    public void addSubSysDictTree(SysDictTree sysDictTree) {
        this.subDictTree.add(sysDictTree);
    }
}
