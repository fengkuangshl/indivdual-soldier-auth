package com.key.win.auth.customer.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.key.win.auth.customer.model.CustomerInfo;
import com.key.win.mybatis.mapper.KeyWinMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerInfoDao extends BaseMapper<CustomerInfo> {
}
