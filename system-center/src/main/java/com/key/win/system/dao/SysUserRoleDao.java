package com.key.win.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.key.win.common.model.system.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

@Mapper
public interface SysUserRoleDao extends BaseMapper<SysUserRole> {

    int deleteUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 根据用户id获取角色
     *
     * @param userId
     * @return
     */
    @Select("select r.* from sys_user_role ru inner join sys_role r on r.id = ru.role_id where ru.user_id = #{userId} and r.enable_flag = 1 and ru.enable_flag = 1")
    List<SysRole> findRolesByUserId(Long userId);

    /**
     * 根据用户ids 获取
     *
     * @param userIds
     * @return
     */

    @Select("<script>select r.* from sys_user_role ru inner join sys_role r on r.id = ru.role_id where ru.user_id IN " +
            " <foreach item='item' index='index' collection='list' open='(' separator=',' close=')'> " +
            " #{item} " +
            " </foreach>" +
            "</script>")
    List<SysRole> findRolesByUserIds(List<Long> userIds);


    void saveBatchUserIdAndRoleIds(@Param("userId") Long userId, @Param("roleIds") Set<Long> roleIds);

    void saveBatchUserIdsAndRoleId(@Param("userIds") Set<Long> userIds, @Param("roleIds") Long roleId);
}
