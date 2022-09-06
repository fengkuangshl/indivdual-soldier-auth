package com.key.win.auth.device.ctrl.device;

import com.key.win.auth.device.model.DeviceAuth;
import com.key.win.auth.device.service.DeviceAuthService;
import com.key.win.auth.util.DeviceAuthUtils;
import com.key.win.basic.exception.BizException;
import com.key.win.basic.web.Result;
import com.key.win.log.annotation.LogAnnotation;
import com.key.win.rsa.web.EncryptResponse;
import com.key.win.websocket.utils.MessageSendUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/*")
public class DeviceEndPoint {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DeviceAuthService deviceAuthService;

    @PostMapping("/auto")
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

    @GetMapping("/deviceToOnLine/{androidId}/{serialNumber}")
    @ApiOperation(value = "设备在线")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public EncryptResponse deviceToOnLine(@PathVariable String androidId, @PathVariable String serialNumber) throws Exception {
        DeviceAuthUtils.setUniqueCodeForOnLine(androidId, serialNumber);
        String uniqueCode = DeviceAuthUtils.getUniqueCode(androidId, serialNumber);
        //MessageSendUtil.sendMessage("设备[" + uniqueCode + "]上线！", uniqueCode);
        return EncryptResponse.succeed();
    }

    @GetMapping("/deviceToOffLine/{androidId}/{serialNumber}")
    @ApiOperation(value = "设备离线")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public EncryptResponse deviceToOffLine(@PathVariable String androidId, @PathVariable String serialNumber) throws Exception {
        DeviceAuthUtils.setUniqueCodeForOffLine(androidId, serialNumber);
        String uniqueCode = DeviceAuthUtils.getUniqueCode(androidId, serialNumber);
        //MessageSendUtil.sendMessage("设备[" + uniqueCode + "]下线！", uniqueCode);
        return EncryptResponse.succeed();
    }
}
