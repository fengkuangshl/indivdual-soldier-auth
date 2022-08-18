package com.key.win.mybatis.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.Map;

public interface KeyWinMapper<T> extends BaseMapper<T> {

    @Select({"${sql} ${ew.customSqlSegment}"})
    IPage<T> selectPageForNativeSql(IPage<T> page, @Param("sql") String sql, @Param("map") Map map, @Param("ew") Wrapper<T> queryWrapper);

    @Select({"${sql} ${ew.customSqlSegment}"})
    ArrayList<T> selectListForNativeSql(@Param("sql") String sql, @Param("ew") Wrapper<T> queryWrapper);
}
