package com.key.win.auth.device.ctrl;


import com.key.win.auth.device.model.DeviceAuth;
import com.key.win.auth.device.service.DeviceAuthService;
import com.key.win.basic.exception.BizException;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.basic.web.Result;
import com.key.win.datalog.annotation.DataLog;
import com.key.win.log.annotation.LogAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public PageResult<DeviceAuth> findDeviceAuthByPaged(@RequestBody PageRequest<DeviceAuth> t) {
        return deviceAuthService.findDeviceAuthByPaged(t);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取客户信息")
    @LogAnnotation(module = "device-auth", recordRequestParam = false)
    public Result get(@PathVariable Long id) {
        return Result.succeed(deviceAuthService.getById(id));
    }

    @DataLog
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    @LogAnnotation(module = "device-auth", recordRequestParam = true)
    public Result delete(@PathVariable Long id) {
        boolean b = deviceAuthService.removeById(id);
        return Result.result(b);
    }

    @DataLog
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "新增/更新")
    @LogAnnotation(module = "device-auth", recordRequestParam = true)
    public Result saveOrUpdate(@RequestBody DeviceAuth deviceAuth) {
        if (StringUtils.isBlank(deviceAuth.getAndroidId())) {
            logger.error("AndroidId不存在！");
            throw new BizException("AndroidId信息不存在!");
        }
        if (StringUtils.isBlank(deviceAuth.getSerialNumber())) {
            logger.error("SerialNumber不存在！");
            throw new BizException("SerialNumber信息不存在!");
        }
        if (StringUtils.isBlank(deviceAuth.getAuthCode())) {
            logger.error("授权码的不存在！");
            throw new BizException("授权码信息不存在!");
        }
        return deviceAuthService.saveOrUpdateDeviceAuth(deviceAuth);
    }

    @GetMapping("/getDeviceAuthAll")
    @ApiOperation(value = "所有认证设备信息")
    @LogAnnotation(module = "device-auth", recordRequestParam = false)
    public Result getDeviceAuthAll() {
        return Result.succeed(deviceAuthService.list());
    }
}
