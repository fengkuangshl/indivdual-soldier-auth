package com.key.win.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.key.win.basic.enumjson.TextureEnumSerializerCode;

@JsonSerialize(using = TextureEnumSerializerCode.class)
public enum PermissionEnum {


    ADD("ADD", "新增权限"),
    UPDATE("MODIFY", "修改权限"),
    DELETE("DELETE", "删除权限"),
    QUERY_PAGED("QUERY::PAGED", "查询分页权限"),
    QUERY_LIST("QUERY::LIST", "查询列表权限"),
    GET_ID("QUERY::ID", "根据ID获取数据权限"),
    USER_UPDATE_ENABLED("USER::UPDATE::ENABLED", "修改用户状态权限"),
    // USER_GET_ON_LINE("USER::GET::ON::LINE", "获所有在线用户权限"),
    USER_GRANTED_ROLE("USER::GRANTED:ROLE", "用户设置角色权限"),
    USER_RESET_PASSWORD("USER::RESET::PASSWORD", "重置密码权限"),
    ROLE_GRANT("ROLE::GRANT", "授权权限"),
    GRANT_PAGE_PERMISSION("GRANT::PAGE::PERMISSION", "菜单页面权限");
    private String code;
    private String text;

    private PermissionEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }


    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
