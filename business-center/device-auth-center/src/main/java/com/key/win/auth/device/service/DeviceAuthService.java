package com.key.win.auth.device.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.key.win.auth.device.model.DeviceAuth;
import com.key.win.auth.device.vo.DeviceAuthResponseVo;
import com.key.win.auth.device.vo.DeviceAuthVo;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;

import java.util.List;

public interface DeviceAuthService extends IService<DeviceAuth> {

    PageResult<DeviceAuthVo> findDeviceAuthByPaged(PageRequest<DeviceAuthVo> t);

    List<DeviceAuth> findDeviceAuth(DeviceAuth deviceAuth);

    DeviceAuthResponseVo saveOrUpdateDeviceAuth(DeviceAuth deviceAuth);

    DeviceAuthResponseVo verificationDevice(DeviceAuth deviceAuth, boolean isAuthorized);

    boolean updateExpireDeviceDate(DeviceAuth deviceAuth);

}
