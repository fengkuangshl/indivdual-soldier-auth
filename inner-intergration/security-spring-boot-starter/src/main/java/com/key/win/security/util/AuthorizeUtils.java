package com.key.win.security.util;

import com.key.win.common.auth.AuthenticationUtil;
import com.key.win.common.auth.detail.Authentication;
import com.key.win.common.model.system.SysMenuPermission;
import com.key.win.common.model.system.SysRole;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthorizeUtils {
    public static Map<String, SysRole> getRole() {
        Authentication authentication = AuthenticationUtil.getAuthentication();
        if (authentication != null) {
            List<SysRole> sysRoles = authentication.getSysRoles();
            if (!CollectionUtils.isEmpty(sysRoles)) {
                return sysRoles.stream().collect(Collectors.toMap(SysRole::getCode, a -> a, (k1, k2) -> k1));
            }
        }
        return null;
    }

    public static Set<String> getPermission() {
        Authentication authentication = AuthenticationUtil.getAuthentication();
        if (authentication != null) {
            List<SysMenuPermission> permissions = authentication.getPermissions();
            if (!CollectionUtils.isEmpty(permissions)) {
                return permissions.stream().map(SysMenuPermission::getPermissionCode).collect(Collectors.toSet());
            }
        }
        return null;
    }
}
