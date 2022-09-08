package com.key.win.auth.device.ctrl.pc;


import com.key.win.auth.device.model.DeviceAuth;
import com.key.win.auth.device.service.DeviceAuthService;
import com.key.win.auth.device.vo.DeviceAuthVo;
import com.key.win.auth.util.DeviceAuthUtils;
import com.key.win.auth.vo.UniqueCodeInfoVo;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.basic.web.Result;
import com.key.win.log.annotation.LogAnnotation;
import com.key.win.rsa.web.EncryptResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/deviceAuth/*")
@Api("客户相关的api")
public class DeviceAuthCtrl {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DeviceAuthService deviceAuthService;

    @PostMapping("/findDeviceAuthByPaged")
    @ApiOperation(value = "客户信息分页")
    @LogAnnotation(module = "device-auth", recordRequestParam = false)
    public PageResult<DeviceAuthVo> findDeviceAuthByPaged(@RequestBody PageRequest<DeviceAuthVo> t) throws Exception {
        List<UniqueCodeInfoVo> onLineDevices = DeviceAuthUtils.getOnLineDevices();
        PageResult<DeviceAuthVo> deviceAuthByPaged = deviceAuthService.findDeviceAuthByPaged(t);
        if (!CollectionUtils.isEmpty(onLineDevices)) {
            Map<String, UniqueCodeInfoVo> uniqueCodeInfoVoMap = onLineDevices.stream().collect(Collectors.toMap(UniqueCodeInfoVo::getUniqueCode, uniqueCodeInfoVo -> uniqueCodeInfoVo));
            List<DeviceAuthVo> data = deviceAuthByPaged.getData();
            if (!CollectionUtils.isEmpty(data)) {
                for (DeviceAuthVo datum : data) {
                    UniqueCodeInfoVo uniqueCodeInfoVo = uniqueCodeInfoVoMap.get(datum.getUniqueCode());
                    if (uniqueCodeInfoVo != null) {
                        datum.setOnLine(uniqueCodeInfoVo.isOnLine());
                    }
                }
            }
        }
        return deviceAuthByPaged;
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取客户信息")
    @LogAnnotation(module = "device-auth", recordRequestParam = false)
    public Result get(@PathVariable Long id) {
        return Result.succeed(deviceAuthService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    @LogAnnotation(module = "device-auth", recordRequestParam = true)
    public Result delete(@PathVariable Long id) {
        boolean b = deviceAuthService.removeById(id);
        return Result.result(b);
    }

    @GetMapping("/getDeviceAuthAll")
    @ApiOperation(value = "所有认证设备信息")
    @LogAnnotation(module = "device-auth", recordRequestParam = false)
    public Result getDeviceAuthAll() {
        return Result.succeed(deviceAuthService.list());
    }

    @PostMapping("/updateExpireDeviceDateAndSendAuthInfo")
    @ApiOperation(value = "所有认证设备信息")
    @LogAnnotation(module = "device-auth", recordRequestParam = false)
    public EncryptResponse updateExpireDeviceDateAndSendAuthInfo(@RequestBody DeviceAuth deviceAuth) {
        return EncryptResponse.succeed(deviceAuthService.updateExpireDeviceDate(deviceAuth));
    }
}
