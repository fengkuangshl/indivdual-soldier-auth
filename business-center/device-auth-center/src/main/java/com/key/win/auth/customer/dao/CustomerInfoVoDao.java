package com.key.win.auth.customer.dao;

import com.key.win.auth.customer.vo.CustomerInfoVo;
import com.key.win.mybatis.mapper.KeyWinMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerInfoVoDao extends KeyWinMapper<CustomerInfoVo> {
}
