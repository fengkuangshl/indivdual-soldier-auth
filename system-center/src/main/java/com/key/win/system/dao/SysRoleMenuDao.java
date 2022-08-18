package com.key.win.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.key.win.common.model.system.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

@Mapper
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenu> {

    int deleteBySelective(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    @Select("select m.* from sys_role_menu t inner join sys_menu m on t.menu_id = m.id where t.role_id = #{roleId} and t.enable_flag = 1 and m.enable_flag =1")
    List<SysMenu> findMenuByRoleId(Long roleId);

    List<SysMenu> findMenusByRoleIds(@Param("roleIds") Set<Long> roleIds);

    void saveBatchRoleIdAndMenuIds(@Param("roleId") Long roleId, @Param("menuIds") Set<Long> menuIds);

    void saveBatchRoleIdsAndMenuId(@Param("roleIds") Set<Long> roleIds, @Param("menuId") Long menuId);
}
