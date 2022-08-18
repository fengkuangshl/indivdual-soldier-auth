package com.key.win.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.key.win.common.model.system.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

@Mapper
public interface SysUserGroupDao extends BaseMapper<SysUserGroup> {

    int deleteUserGroup(@Param("userId") Long userId, @Param("groupId") Long groupId);

    /**
     * 根据用户id获取组信息
     *
     * @param userId
     * @return
     */
    @Select("select g.* from sys_user_group gu inner join sys_group g on g.id = gu.group_id where gu.user_id = #{userId} and g.enable_flag = 1 and gu.enable_flag = 1")
    List<SysGroup> findGroupByUserId(Long userId);

    /**
     * 根据用户id获取组信息
     *
     * @param groupId
     * @return
     */
    @Select("select g.* from sys_user_group gu inner join sys_user g on g.id = gu.user_id where gu.group_id = #{groupId} and g.enable_flag = 1 and gu.enable_flag = 1")
    List<SysUser> findUserByGroupId(Long groupId);

    /**
     * 根据用户id获取组信息
     *
     * @param groupId
     * @return
     */
    @Select("select g.* from sys_user_group gu inner join sys_user g on g.id = gu.user_id where gu.group_id = #{groupId} and g.enable_flag = 1 and gu.enable_flag = 1")
    List<SysUser> findUserPagedByGroupId(IPage<SysUser> page, Long groupId);

    /**
     * 根据用户ids 获取
     *
     * @param userIds
     * @return
     */

    @Select("<script>select u.* from sys_user_group gu inner join sys_group u on u.id = gu.group_id where gu.user_id IN " +
            " <foreach item='item' index='index' collection='list' open='(' separator=',' close=')'> " +
            " #{item} " +
            " </foreach>" +
            "</script>")
    List<SysGroup> findGroupByUserIds(List<Long> userIds);

    void saveBatchUserIdAndGroupIds(@Param("userId") Long userId, @Param("groupIds") Set<Long> groupIds);

    void saveBatchUserIdsAndGroupId(@Param("userIds") Set<Long> userIds, @Param("groupId") Long groupId);
}
