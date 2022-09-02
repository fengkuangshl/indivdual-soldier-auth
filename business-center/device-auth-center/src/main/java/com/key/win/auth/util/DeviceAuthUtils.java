package com.key.win.auth.util;

import com.key.win.auth.vo.UniqueCodeInfoVo;
import com.key.win.basic.util.IndividualSoldierAuthConstantUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

public class DeviceAuthUtils {
    private static final Logger logger = LoggerFactory.getLogger(DeviceAuthUtils.class);

    public static final String REDIS_UNIQUE_CODE_KEY_PREFIX = IndividualSoldierAuthConstantUtils.REDIS_ROOT_KEY_PREFIX + "device::unique_code::";
    private static RedisTemplate<String, Object> redisTemplate;
    private static int uniqueCodeExpires;


    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        DeviceAuthUtils.redisTemplate = redisTemplate;
    }

    @Value("${spring.global.device.unique-code.expires:5}")
    public void setTokenExpires(int expires) {
        uniqueCodeExpires = expires;
    }


    /**
     * 根据androidId和serialNumber经Md5生成唯一码
     *
     * @param androidId
     * @param serialNumber
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getUniqueCode(String androidId, String serialNumber) {
        String newStr = androidId + serialNumber;
        String uniqueCode = null;
        try {
            uniqueCode = DigestUtils.md5DigestAsHex(newStr.getBytes("utf-8"));
            logger.info("[androidId:{},serialNumber:{}]md5加密后:{}", androidId, serialNumber, uniqueCode);
        } catch (UnsupportedEncodingException e) {
            logger.error("[androidId:{},serialNumber:{}]md5加密出错:{}", androidId, serialNumber, e.getMessage(), e);
        }

        return uniqueCode;
    }


    public static String getRedisUniqueCodeKey(String uniqueCode) {
        return REDIS_UNIQUE_CODE_KEY_PREFIX + uniqueCode;
    }

    public static void setUniqueCodeForOnLine(String androidId, String serialNumber) {
        setUniqueCodeToRedis(androidId, serialNumber, Boolean.TRUE);
    }

    public static void setUniqueCodeForOffLine(String androidId, String serialNumber) {
        setUniqueCodeToRedis(androidId, serialNumber, Boolean.FALSE);
    }

    public static UniqueCodeInfoVo getUniqueCodeToRedis(String androidId, String serialNumber) {
        return getUniqueCodeToRedis(getUniqueCode(androidId, serialNumber));
    }

    public static UniqueCodeInfoVo getUniqueCodeToRedis(String uniqueCode) {
        UniqueCodeInfoVo uniqueCodeInfoVo = (UniqueCodeInfoVo) redisTemplate.opsForValue().get(getRedisUniqueCodeKey(uniqueCode));
        return uniqueCodeInfoVo;
    }

    public static void setUniqueCodeToRedis(String androidId, String serialNumber, boolean isOnLine) {
        String uniqueCode = getUniqueCode(androidId, serialNumber);
        UniqueCodeInfoVo uniqueCodeInfoVo = new UniqueCodeInfoVo(androidId, serialNumber, uniqueCode, isOnLine);
        redisTemplate.opsForValue().set(getRedisUniqueCodeKey(uniqueCode), uniqueCodeInfoVo, uniqueCodeExpires, TimeUnit.SECONDS);
    }

}
