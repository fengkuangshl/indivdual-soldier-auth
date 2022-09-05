package com.key.win.auth.device.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.key.win.auth.customer.model.CustomerInfo;
import com.key.win.auth.device.model.DeviceAuth;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceAuthDao extends BaseMapper<DeviceAuth> {
}
