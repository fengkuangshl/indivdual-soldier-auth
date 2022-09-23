package com.key.win.auth.device.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.key.win.auth.device.model.DeviceAuth;
import com.key.win.auth.device.vo.DeviceAuthVo;
import com.key.win.mybatis.mapper.KeyWinMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceAuthVoDao extends KeyWinMapper<DeviceAuthVo> {
}
