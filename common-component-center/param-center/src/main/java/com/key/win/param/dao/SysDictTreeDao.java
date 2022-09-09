package com.key.win.param.dao;

import com.key.win.mybatis.mapper.KeyWinMapper;
import com.key.win.param.model.SysDictTree;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysDictTreeDao extends KeyWinMapper<SysDictTree> {

    @Select(" select dt.* from sys_dict_tree dt where (select count(1) from sys_dict_tree e where e.parent_id=dt.id) = 0 and dt.enable_flag = 1 and dt.type=#{type}")
    List<SysDictTree> findLeafNode(Long type);

    @Select("SELECT dt.* FROM ( SELECT @pId AS pId, ( SELECT @pId := GROUP_CONCAT(id) FROM sys_dict_tree WHERE FIND_IN_SET(parent_Id, @pId)) AS p FROM sys_dict_tree, (SELECT @pId := #{parentId}) b WHERE @pId IS NOT NULL ) temp, sys_dict_tree dt WHERE FIND_IN_SET(dt.parent_Id, temp.pId) AND dt.enable_flag = 1")
    List<SysDictTree> findChildrenNode(Long parentId);


    @Select(" SELECT dt.* FROM ( SELECT @pId AS pId, ( SELECT @pId := parent_id FROM sys_dict_tree WHERE FIND_IN_SET(id, @pId)) AS p FROM sys_dict_tree, ( SELECT @pId := #{id} ) b WHERE @pId IS NOT NULL ) temp, sys_dict_tree dt WHERE FIND_IN_SET(dt.id, temp.pId) AND dt.enable_flag = 1 ")
    List<SysDictTree> findParentNode(Long id);
}
