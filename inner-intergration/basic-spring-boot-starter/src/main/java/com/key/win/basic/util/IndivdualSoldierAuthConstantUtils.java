package com.key.win.basic.util;

public class IndivdualSoldierAuthConstantUtils {


    public static final String MODEL_ID = "id";
    public static final String MODEL_CREATE_DATE = "createDate";
    public static final String MODEL_UPDATE_DATE = "updateDate";
    public static final String MODEL_CREATE_USER_ID = "createUserId";
    public static final String MODEL_UPDATE_USER_ID = "updateUserId";
    public static final String MODEL_CREATE_USER_NAME = "createUserName";
    public static final String MODEL_UPDATE_USER_NAME = "updateUserName";
    public static final String MODEL_ENABLE_FLAG = "enableFlag";
    public static final String MODEL_ID_MONGO = "_id";

    public static final String REDIS_ROOT_KEY_PREFIX = "indivdual_soldier_wireless_auth:";
    public static final String TOKEN = "token";
    public static final String REFRESH_TOKEN = "refresh:" + TOKEN;
    public static final String REDIS_TOKEN_KEY_PREFIX = REDIS_ROOT_KEY_PREFIX + TOKEN + ":";
    public static final String REDIS_REFRESH_TOKEN_KEY_PREFIX = REDIS_ROOT_KEY_PREFIX + REFRESH_TOKEN + ":";
    public static final String TOKEN_BEARER_KEY = "token_type";
    public static final String TOKEN_BEARER_VAUE = "bearer";
    public static final String TOKEN_EXPIRES_IN_KEY = "expires_in";
    public static final int REFRESH_TOKEN_EXPIRES_IN_VALUE = 3600 * 24 * 7;
    public static final int TOKEN_EXPIRES_IN_VALUE = 3600 * 24 * 1;
    public static final int WEB_SOCKET_DISCONNECT_TOKEN_WAIT_TIME = 60;
    public static final String REQUEST_HEADER_AUTHORIZATION = "Authorization";
    public static final String REQUEST_TOKEN_KEY = "access_token";
    public static final String REFRESH_TOKEN_KEY = "refresh_token";
    public static final String SYSTEM_ANONYMOUS_USER = "anonymous";
    public static final Long SYSTEM_ANONYMOUS_USER_ID = -1L;
    public static final Long TREE_PARENT_ID = -1l;
    public static final String RESET_PASSWORD = "123456";
    public static final String RESULT_SUCCESS_DEFAULT_MESSAGE = "OK";
    public static final String QUERY_DEFAULT_ORDER_NAME = "createDate";
    public static final String SQL_SEPARATOR = " ";
    public static final String ORDER_BY = SQL_SEPARATOR + "ORDER BY" + SQL_SEPARATOR;
    public static final String SYMBOL_COMMA = ",";
    public static final String SQL_COMMA_SEPARATOR = SQL_SEPARATOR + SYMBOL_COMMA + SQL_SEPARATOR;

    public static final String SUCCEED_INFO ="操作成功";
    public static final String FAILED_INFO ="操作成功";

    public static String getRedisTokenKey(String token) {
        return IndivdualSoldierAuthConstantUtils.REDIS_TOKEN_KEY_PREFIX + token;
    }

    public static String getRedisRefreshTokenKey(String refreshToken) {
        return IndivdualSoldierAuthConstantUtils.REDIS_REFRESH_TOKEN_KEY_PREFIX + refreshToken;
    }


}
