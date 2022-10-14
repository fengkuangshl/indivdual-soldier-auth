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

@ApiModel("菜单实体")
@Data
@TableName("sys_menu")
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends MybatisID {
    @ApiModelProperty("父节点Id")
    private Long parentId;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("url")
    private String url;
    @ApiModelProperty("路径")
    private String path;
    @ApiModelProperty("css")
    private String css;
    @ApiModelProperty("排序")
    private Integer sort;
    @ApiModelProperty("是否菜单：1-目录，2-菜单")
    private Integer isMenu;
    @ApiModelProperty("是否隐藏：true-是 false-否")
    private Boolean isHidden;
    @ApiModelProperty("孩子列表")
    @TableField(exist = false)
    private List<SysMenu> subMenus = new ArrayList<>();
    @ApiModelProperty("菜单Id集合")
    @TableField(exist = false)
    private Set<Long> menuIds;

    public void addSubMenu(SysMenu sysMenu) {
        this.subMenus.add(sysMenu);
    }


}
