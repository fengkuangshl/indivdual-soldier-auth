package com.key.win.common.auth;

import com.key.win.basic.exception.UserIllegalException;
import com.key.win.basic.util.IndividualSoldierAuthConstantUtils;
import com.key.win.common.auth.detail.Authentication;
import com.key.win.common.model.system.SysUser;
import com.key.win.common.vo.RefreshTokenVo;
import com.key.win.redis.util.RedisScanUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class AuthenticationUtil {

    private final static Logger logger = LoggerFactory.getLogger(AuthenticationUtil.class);

    private static final ThreadLocal<Authentication> userDetailsHolder = new ThreadLocal<Authentication>();

    private static RedisTemplate<String, Object> redisTemplate;

    private static int tokenExpires;

    private static int refreshTokenExpires;

    private static boolean isRefreshSelf;


    public static String getNickName() {
        Authentication loginUser = getAuthentication();
        if (loginUser != null) {
            return loginUser.getNickName();
        }
        return IndividualSoldierAuthConstantUtils.SYSTEM_ANONYMOUS_USER;
    }

    public static void clearContext() {
        userDetailsHolder.remove();
    }

    public static Authentication createEmptyContext() {
        Authentication authentication = new Authentication();
        setCurrentUser(authentication);
        return authentication;
    }

    @Value("${spring.global.token.expires:86400}")
    public void setTokenExpires(int expires) {
        AuthenticationUtil.tokenExpires = expires;
    }

    @Value("${spring.global.refresh.token.expires:604800}")
    public void setRefreshTokenExpires(int expires) {
        AuthenticationUtil.refreshTokenExpires = expires;
    }

    @Value("${spring.global.refresh.token.isRefreshSelf:false}")
    public void setIsRefreshSelf(boolean b) {
        AuthenticationUtil.isRefreshSelf = b;
    }

    public static int getTokenExpires() {
        return tokenExpires;
    }

    public static int getRefreshTokenExpires() {
        return refreshTokenExpires;
    }

    public static boolean isIsRefreshSelf() {
        return isRefreshSelf;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        AuthenticationUtil.redisTemplate = redisTemplate;
    }

    /**
     * 获取登陆的 LoginAppUser
     *
     * @return
     */
    public static Authentication getAuthentication() {
        return userDetailsHolder.get();
    }

    /**
     * 设置当前用户Model
     *
     * @param sysUser
     */
    public static void setCurrentUser(Authentication sysUser) {
        userDetailsHolder.set(sysUser);
    }

    public static String getUserName() {
        Authentication loginUser = getAuthentication();
        if (loginUser != null) {
            return loginUser.getUserName();
        }
        return IndividualSoldierAuthConstantUtils.SYSTEM_ANONYMOUS_USER;
    }

    public static Long getUserId() {
        Authentication loginUser = getAuthentication();
        if (loginUser != null) {
            return loginUser.getId();
        }
        return IndividualSoldierAuthConstantUtils.SYSTEM_ANONYMOUS_USER_ID;
    }

    public static String getHeadImgUrl() {
        Authentication loginUser = getAuthentication();
        if (loginUser != null) {
            return loginUser.getHeadImgUrl();
        }
        return "";
    }

    public static boolean isAnonymousUser() {
        Authentication loginUser = getAuthentication();
        if (loginUser == null) {
            return true;
        }
        return false;
    }

    public static String getToken() {
        Authentication loginUser = getAuthentication();
        if (loginUser != null) {
            return loginUser.getToken();
        }
        return "";
    }

    public static String getTokenException() {
        String token = getToken();
        if (StringUtils.isBlank(token)) {
            throw new UserIllegalException("token不存在！");
        }
        return token;
    }

    public static String getRefreshTokenException() {
        String token = getRefreshToken();
        if (StringUtils.isBlank(token)) {
            throw new UserIllegalException("refreshtoken不存在！");
        }
        return token;
    }

    private static String getRefreshToken() {
        Authentication loginUser = getAuthentication();
        if (loginUser != null) {
            return loginUser.getRefreshToken();
        }
        return "";
    }

    public static void setAuthenticationToRedis(Authentication authentication) {
        setAuthenticationToRedis(authentication, tokenExpires);
    }

    public static void setAuthenticationToRedis(Authentication authentication, int tokenExpires) {
        redisTemplate.opsForValue().set(IndividualSoldierAuthConstantUtils.getRedisTokenKey(authentication.getToken()), authentication, tokenExpires, TimeUnit.SECONDS);
    }

    public static void setAuthenticationTokenExpires(String token) {
        redisTemplate.expire(IndividualSoldierAuthConstantUtils.getRedisTokenKey(token), tokenExpires, TimeUnit.SECONDS);
    }

    public static Authentication getAuthenticationToRedis() {
        return getAuthenticationToRedis(getTokenException());
    }

    public static Authentication getAuthenticationToRedis(String token) {
        return (Authentication) redisTemplate.opsForValue().get(IndividualSoldierAuthConstantUtils.getRedisTokenKey(token));
    }

    public static boolean deleteTokenToRedis(String token) {
        return redisTemplate.delete(IndividualSoldierAuthConstantUtils.getRedisTokenKey(token));
    }

    public static boolean logout() {
        deleteRefreshTokenToRedis(getRefreshTokenException());
        return deleteTokenToRedis(getTokenException());
    }

    public static List<Authentication> getOnLineUser() throws Exception {
        List<Authentication> authenticationList = new ArrayList<>();
        Cursor<String> cursor = RedisScanUtil.scan(redisTemplate, IndividualSoldierAuthConstantUtils.REDIS_TOKEN_KEY_PREFIX + "*", 999);
        while (cursor.hasNext()) {
            Authentication authentication = (Authentication) redisTemplate.opsForValue().get(cursor.next());
            authenticationList.add(authentication);
        }
        cursor.close();
        return authenticationList;
    }

    public static Map<Long, Authentication> getOnLineUserToMap() {
        try {
            return AuthenticationUtil.getOnLineUser().stream().collect(Collectors.toMap(Authentication::getId, a -> a, (k1, k2) -> k1));
        } catch (Exception e) {
            logger.error("getOnLineUserToMap is error:{}!", e.getMessage(), e);
            return null;
        }
    }

    public static Authentication getOnLineUserByUserId(Long userId) {
        Map<Long, Authentication> onLineUserToMap = AuthenticationUtil.getOnLineUserToMap();
        if (CollectionUtils.isEmpty(onLineUserToMap)) {
            return null;
        }
        return onLineUserToMap.get(userId);
    }

    public static RefreshTokenVo getRefreshTokenToRedis(String refreshToken) {
        return (RefreshTokenVo) redisTemplate.opsForValue().get(IndividualSoldierAuthConstantUtils.getRedisRefreshTokenKey(refreshToken));
    }

    public static void setRefreshTokenToRedis(String refreshToken) {
        RefreshTokenVo refreshTokenVo = new RefreshTokenVo();
        refreshTokenVo.setToken(getToken());
        refreshTokenVo.setUserName(getUserName());
        setRefreshTokenToRedis(refreshToken, refreshTokenVo);
    }

    public static void setRefreshTokenToRedis(String refreshToken, Authentication authentication) {
        RefreshTokenVo refreshTokenVo = new RefreshTokenVo();
        refreshTokenVo.setToken(authentication.getToken());
        refreshTokenVo.setUserName(authentication.getUserName());
        setRefreshTokenToRedis(refreshToken, refreshTokenVo);
    }

    public static void setRefreshTokenToRedis(String refreshToken, RefreshTokenVo refreshTokenVo) {
        redisTemplate.opsForValue().set(IndividualSoldierAuthConstantUtils.getRedisRefreshTokenKey(refreshToken), refreshTokenVo, refreshTokenExpires, TimeUnit.SECONDS);
    }

    public static void deleteRefreshTokenToRedis(String refreshToken) {
        redisTemplate.delete(IndividualSoldierAuthConstantUtils.getRedisRefreshTokenKey(refreshToken));
    }


    public static List<SysUser> getSysUsersAndState(List<SysUser> sysUserByOrganId) throws Exception {
        List<Authentication> onLineUser = AuthenticationUtil.getOnLineUser();
        List<SysUser> allSysUser = new ArrayList<>();
        if (!CollectionUtils.isEmpty(onLineUser)) {
            Map<Long, List<Authentication>> onLineUserMap = onLineUser.stream().collect(Collectors.groupingBy(Authentication::getId));
            sysUserByOrganId.forEach(u -> {
                List<Authentication> authentications = onLineUserMap.get(u.getId());
                if (authentications != null) {
                    authentications.forEach(authentication -> {
                        copyUser(allSysUser, true, authentication.getId(), authentication.getNickName(), authentication.getUserName(), authentication.getToken());
                    });
                } else {
                    copyUser(allSysUser, false, u.getId(), u.getNickName(), u.getUserName(), "");
                }
            });
        }
        return allSysUser;
    }

    public static List<SysUser> getSysUsersForOnLine(List<SysUser> sysUserByOrganId) throws Exception {
        List<Authentication> onLineUser = AuthenticationUtil.getOnLineUser();
        List<SysUser> allSysUser = new ArrayList<>();
        if (!CollectionUtils.isEmpty(onLineUser)) {
            Map<Long, List<Authentication>> onLineUserMap = onLineUser.stream().collect(Collectors.groupingBy(Authentication::getId));
            sysUserByOrganId.forEach(u -> {
                List<Authentication> authentications = onLineUserMap.get(u.getId());
                if (authentications != null) {
                    authentications.forEach(authentication -> {
                        copyUser(allSysUser, true, authentication.getId(), authentication.getNickName(), authentication.getUserName(), authentication.getToken());
                    });
                }
            });
        }
        return allSysUser;
    }

    public static void copyUser(List<SysUser> allSysUser, boolean b, Long id, String nickName, String userName, String token) {
        SysUser sysUser = new SysUser();
        sysUser.setOnLine(b);
        sysUser.setId(id);
        sysUser.setToken(token);
        sysUser.setNickName(nickName);
        sysUser.setUserName(userName);
        allSysUser.add(sysUser);
    }
}
