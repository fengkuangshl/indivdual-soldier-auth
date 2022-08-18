package com.key.win.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.key.win.common.model.system.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;


@Mapper
public interface SysRolePermissionDao extends BaseMapper<SysRolePermission> {


    int deleteBySelective(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    List<SysPermission> findByRoleIds(@Param("roleIds") Set<Long> roleIds);

    @Select("select m.* from sys_role_permission t inner join sys_permission m on t.permission_id = m.id where t.role_id = #{roleId} and t.enable_flag = 1 and m.enable_flag =1")
    List<SysPermission> findSysPermissionByRoleId(Long roleId);

    void saveBatchRoleIdAndPermissions(@Param("roleId") Long roleId, @Param("permissions") Set<Long> permissions);

    void saveBatchRoleIdsAndPermissionId(@Param("roleIds") Set<Long> roleIds, @Param("permissionId") Long permissionId);
}
