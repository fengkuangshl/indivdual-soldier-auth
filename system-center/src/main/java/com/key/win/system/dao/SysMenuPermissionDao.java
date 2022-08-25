package com.key.win.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.key.win.common.model.system.SysMenu;
import com.key.win.common.model.system.SysMenuPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

@Mapper
public interface SysMenuPermissionDao extends BaseMapper<SysMenuPermission> {

    int deleteBySelective(@Param("permissionId") Long permissionId, @Param("menuId") Long menuId);

    @Select("select m.* from sys_menu_permission t inner join sys_menu m on t.menu_id = m.id where t.permission_Id = #{permissionId} and t.enable_flag = 1 and m.enable_flag =1")
    List<SysMenu> findMenuByPermissionId(Long permissionId);

    @Select("select p.* from sys_menu_permission t inner join sys_permission p on t.permission_id = p.id where p.menu_id = #{menuId} and t.enable_flag = 1 and m.enable_flag =1")
    List<SysMenu> findPermissionByMenuId(Long menuId);

    List<SysMenu> findMenusByPermissionIds(@Param("permissionIds") Set<Long> permissionIds);

    void saveBatchPermissionIdAndMenuIds(@Param("permissionId") Long permissionId, @Param("menuIds") Set<Long> menuIds, @Param("permissionCode") String permissionCode);

    void saveBatchPermissionIdsAndMenuId(@Param("permissionIds") Set<Long> permissionIds, @Param("menuId") Long menuId, @Param("permissionCode") String permissionCode);
}
