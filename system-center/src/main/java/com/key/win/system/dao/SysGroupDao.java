package com.key.win.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.key.win.common.model.system.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysGroupDao extends BaseMapper<SysGroup> {

    @Select(" select o.* from sys_group o where (select count(1) from sys_group e where e.parent_id=o.id) = 0 and o.enable_flag = 1")
    List<SysGroup> findLeafNode();
}
