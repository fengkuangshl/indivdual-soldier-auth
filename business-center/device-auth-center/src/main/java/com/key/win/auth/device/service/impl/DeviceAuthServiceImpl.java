package com.key.win.auth.device.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.key.win.auth.customer.model.CustomerInfo;
import com.key.win.auth.customer.service.CustomerInfoService;
import com.key.win.auth.device.dao.DeviceAuthDao;
import com.key.win.auth.device.dao.DeviceAuthVoDao;
import com.key.win.auth.device.model.DeviceAuth;
import com.key.win.auth.device.service.DeviceAuthService;
import com.key.win.auth.device.vo.DeviceAuthResponseVo;
import com.key.win.auth.device.vo.DeviceAuthVo;
import com.key.win.auth.util.DeviceAuthUtils;
import com.key.win.auth.vo.UniqueCodeInfoVo;
import com.key.win.basic.exception.BizException;
import com.key.win.basic.util.BeanUtils;
import com.key.win.basic.util.DateUtils;
import com.key.win.basic.util.JsonUtils;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.basic.web.Result;
import com.key.win.datalog.service.SysDataLogService;
import com.key.win.mybatis.page.MybatisPageServiceTemplate;
import com.key.win.rsa.RSAEncryptor;
import com.key.win.rsa.exception.BizEncryptException;
import com.key.win.rsa.util.RSAUtils;
import com.key.win.rsa.util.SignUtils;
import com.key.win.rsa.web.EncryptModel;
import com.key.win.websocket.utils.MessageSendUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class DeviceAuthServiceImpl extends ServiceImpl<DeviceAuthDao, DeviceAuth> implements DeviceAuthService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String tableName = "DEVICE_AUTH";

    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private SysDataLogService sysDataLogService;
    @Autowired
    private DeviceAuthVoDao deviceAuthVoDao;

    @Override
    public PageResult<DeviceAuthVo> findDeviceAuthByPaged(PageRequest<DeviceAuthVo> t) {
        MybatisPageServiceTemplate<DeviceAuthVo, DeviceAuthVo> query = new MybatisPageServiceTemplate<DeviceAuthVo, DeviceAuthVo>(deviceAuthVoDao) {
            @Override
            protected AbstractWrapper constructWrapper(DeviceAuthVo deviceAuth) {
                return buildDeviceAuthLambdaQueryWrapper(deviceAuth);
            }

            //select da.*,dci.company_name,dci.project_no,dci.enabled_verification from device_auth da INNER JOIN device_customer_info dci on da.auth_code = dci.auth_device_code
            protected String constructNativeSql() {
                return "select * from (select da.*,dci.company_name,dci.project_no,dci.is_verify,dci.project_name,dci.sequence from device_auth da INNER JOIN device_customer_info dci on da.auth_code = dci.auth_device_code where da.enable_flag = 1 and  dci.enable_flag = 1 ) tmp";
            }
        };
        return query.doPagingQuery(t);
    }

    @Override
    public List<DeviceAuth> findDeviceAuth(DeviceAuth deviceAuth) {
        LambdaQueryWrapper<DeviceAuth> lambdaQueryWrapper = new LambdaQueryWrapper<DeviceAuth>();
        if (deviceAuth != null) {
            if (StringUtils.isNotBlank(deviceAuth.getUniqueCode())) {
                lambdaQueryWrapper.eq(DeviceAuth::getUniqueCode, deviceAuth.getUniqueCode());
            }
            if (StringUtils.isNotBlank(deviceAuth.getAndroidId())) {
                lambdaQueryWrapper.eq(DeviceAuth::getAndroidId, deviceAuth.getAndroidId());
            }
            if (StringUtils.isNotBlank(deviceAuth.getSerialNumber())) {
                lambdaQueryWrapper.like(DeviceAuth::getSerialNumber, deviceAuth.getSerialNumber());
            }
            if (StringUtils.isNotBlank(deviceAuth.getAuthCode())) {
                lambdaQueryWrapper.eq(DeviceAuth::getAuthCode, deviceAuth.getAuthCode());
            }
        }
        return this.list(lambdaQueryWrapper);
    }

    private LambdaQueryWrapper<DeviceAuthVo> buildDeviceAuthLambdaQueryWrapper(DeviceAuthVo deviceAuth) {
        LambdaQueryWrapper<DeviceAuthVo> lambdaQueryWrapper = new LambdaQueryWrapper<DeviceAuthVo>();
        if (deviceAuth != null) {
            if (StringUtils.isNotBlank(deviceAuth.getUniqueCode())) {
                lambdaQueryWrapper.eq(DeviceAuthVo::getUniqueCode, deviceAuth.getUniqueCode());
            }
            if (StringUtils.isNotBlank(deviceAuth.getAndroidId())) {
                lambdaQueryWrapper.eq(DeviceAuthVo::getAndroidId, deviceAuth.getAndroidId());
            }
            if (StringUtils.isNotBlank(deviceAuth.getSerialNumber())) {
                lambdaQueryWrapper.like(DeviceAuthVo::getSerialNumber, deviceAuth.getSerialNumber());
            }
            if (StringUtils.isNotBlank(deviceAuth.getAuthCode())) {
                lambdaQueryWrapper.eq(DeviceAuthVo::getAuthCode, deviceAuth.getAuthCode());
            }

            if (StringUtils.isNotBlank(deviceAuth.getProjectNo())) {
                lambdaQueryWrapper.eq(DeviceAuthVo::getProjectNo, deviceAuth.getProjectNo());
            }
            if (StringUtils.isNotBlank(deviceAuth.getStartDate()) && StringUtils.isNotBlank(deviceAuth.getEndDate())) {
                lambdaQueryWrapper.between(DeviceAuthVo::getExpireDeviceDate, DateUtils.strToTime(deviceAuth.getStartDate()), DateUtils.strToTime(deviceAuth.getEndDate()));
            }
            if (StringUtils.isNotBlank(deviceAuth.getStartNum()) && StringUtils.isNotBlank(deviceAuth.getEndNum())) {
                lambdaQueryWrapper.between(DeviceAuthVo::getAuthDeviceNum, deviceAuth.getStartNum(), deviceAuth.getEndNum());
            }
            if (StringUtils.isNotBlank(deviceAuth.getLeadMobile())) {
                lambdaQueryWrapper.eq(DeviceAuthVo::getLeadMobile, deviceAuth.getLeadMobile());
            }
            if (StringUtils.isNotBlank(deviceAuth.getLeadName())) {
                lambdaQueryWrapper.like(DeviceAuthVo::getLeadName, deviceAuth.getLeadName());
            }
        }
        return lambdaQueryWrapper;
    }

    public int getCountByAuthCode(String authCode) {
        LambdaQueryWrapper<DeviceAuth> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DeviceAuth::getAuthCode, authCode);
        return super.count(lambdaQueryWrapper);
    }

    public DeviceAuth getDeviceAuthByUniqueCode(String uniqueCode) {
        LambdaQueryWrapper<DeviceAuth> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DeviceAuth::getUniqueCode, uniqueCode);
        List<DeviceAuth> list = super.list(lambdaQueryWrapper);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    private String getDataLogFkId(Long id) {
        return tableName +"::"+ id;
    }

    @Override
    public synchronized DeviceAuthResponseVo saveOrUpdateDeviceAuth(DeviceAuth deviceAuth) {
        deviceAuth.setUniqueCode(DeviceAuthUtils.getUniqueCode(deviceAuth.getAndroidId(), deviceAuth.getSerialNumber()));//???????????????
        DeviceAuth deviceAuthByUniqueCode = getDeviceAuthByUniqueCode(deviceAuth.getUniqueCode());
        if (deviceAuthByUniqueCode != null) {
            BeanUtils.copyPropertiesToPartField(deviceAuth, deviceAuthByUniqueCode);
            if (super.updateById(deviceAuthByUniqueCode)) {
                sysDataLogService.saveDataLog("?????????????????????????????????????????????????????????", getDataLogFkId(deviceAuthByUniqueCode.getId()));
            }
            return verificationDevice(deviceAuthByUniqueCode, true);
        }
        CustomerInfo customerByAuthCode = customerInfoService.findCustomerByAuthCode(deviceAuth.getAuthCode());//??????????????????
        if (customerByAuthCode != null) {
            deviceAuth.setExpireDeviceDate(customerByAuthCode.getExpireDeviceDate());
        }
        if (super.save(deviceAuth)) {
            sysDataLogService.saveDataLog("???????????????????????????????????????????????????", getDataLogFkId(deviceAuth.getId()));
        }
        return verificationDevice(deviceAuth, false);
    }

    public DeviceAuthResponseVo verificationDevice(DeviceAuth deviceAuth, boolean isAuthorized) {
        int count = getCountByAuthCode(deviceAuth.getAuthCode());//????????????????????????
        CustomerInfo customerByAuthCode = customerInfoService.findCustomerByAuthCode(deviceAuth.getAuthCode());//??????????????????
        if (customerByAuthCode != null) {
            if (count <= customerByAuthCode.getAuthDeviceNum() || isAuthorized) { //????????????
                DeviceAuthResponseVo deviceAuthResponseVo = new DeviceAuthResponseVo();
                if (customerByAuthCode.getIsVerify()) {//????????????????????????
                    Date expireDeviceDate = customerByAuthCode.getExpireDeviceDate();
                    if (deviceAuth.getExpireDeviceDate() != null) {
                        expireDeviceDate = deviceAuth.getExpireDeviceDate();
                    } else {
                        deviceAuth.setExpireDeviceDate(expireDeviceDate);
                        super.updateById(deviceAuth);//??????????????????????????????
                    }
                    String encodeStr = Base64Utils.encodeToString((expireDeviceDate.getTime() + "").getBytes());
                    String reverseStr = new StringBuffer(encodeStr).reverse().toString();
                    deviceAuthResponseVo.setAuthInfo(deviceAuth.getUniqueCode() + deviceAuth.getVerifyCode() + "=" + reverseStr + "y");
                    sysDataLogService.saveDataLog("???????????????????????????????????????[" + DateUtils.dateToStr(expireDeviceDate) + "]", getDataLogFkId(deviceAuth.getId()));
                } else {
                    deviceAuthResponseVo.setAuthInfo(deviceAuth.getUniqueCode() + "n");
                    sysDataLogService.saveDataLog("??????????????????????????????????????????", getDataLogFkId(deviceAuth.getId()));
                }
                return deviceAuthResponseVo;
            } else {
                logger.warn("????????????????????????????????????[{}]???????????????????????????", customerByAuthCode.getAuthDeviceNum());
                sysDataLogService.saveDataLog("????????????????????????????????????[" + customerByAuthCode.getAuthDeviceNum() + "]???????????????????????????", getDataLogFkId(deviceAuth.getId()));
                throw new BizException("????????????????????????????????????[" + customerByAuthCode.getAuthDeviceNum() + "]???????????????????????????");
            }
        } else {
            sysDataLogService.saveDataLog("?????????????????????????????????????????????????????????", getDataLogFkId(deviceAuth.getId()));
            throw new BizException("?????????????????????????????????????????????????????????");
        }
    }

    @Override
    public boolean updateExpireDeviceDate(DeviceAuth deviceAuth) {
        if (deviceAuth == null) {
            logger.error("???????????????????????????");
            throw new BizException("???????????????????????????????????????");
        }
        if (deviceAuth.getId() != null) {
            DeviceAuth byId = super.getById(deviceAuth.getId());
            if (byId != null) {
                if (deviceAuth.getExpireDeviceDate() != null) {
                    byId.setExpireDeviceDate(deviceAuth.getExpireDeviceDate());
                    boolean b = super.updateById(byId);
                    if (b) {
                        sysDataLogService.saveDataLog("?????????????????????????????????[" + DateUtils.dateToStr(deviceAuth.getExpireDeviceDate()) + "]", getDataLogFkId(deviceAuth.getId()));
                    }
                }
                DeviceAuthResponseVo deviceAuthResponseVo = verificationDevice(byId, true);
                sendAuthInfo(deviceAuthResponseVo, byId);
                return true;
            }

        }
        return false;
    }

    public void sendAuthInfo(DeviceAuthResponseVo deviceAuthResponseVo, DeviceAuth deviceAuth) {
        UniqueCodeInfoVo uniqueCodeToRedis = DeviceAuthUtils.getUniqueCodeToRedis(deviceAuth.getUniqueCode());
        if (uniqueCodeToRedis != null) {
//            EncryptModel out = new EncryptModel();
//            out.setTimestamp(System.currentTimeMillis());
//            try {
//                RSAEncryptor rsaEncryptor = new RSAEncryptor(RSAUtils.privateKeyPath, RSAUtils.publicKeyPath);
//                out.setData(rsaEncryptor.encryptWithBase64(JsonUtils.toJsonNoException(deviceAuthResponseVo)));
//                String rawSign = String.format("data=%s&timestamp=%d", out.getData(), out.getTimestamp());
//                out.setSign(SignUtils.sha(rawSign));
//            } catch (Exception e) {
//                logger.error("??????????????????:" + e.getMessage(), e);
//                throw new BizEncryptException("??????????????????!");
//            }
            MessageSendUtil.sendMessage(deviceAuthResponseVo, uniqueCodeToRedis.getUniqueCode());
        } else {
            throw new BizException("??????????????????,???????????????????????????");
        }
    }
}
