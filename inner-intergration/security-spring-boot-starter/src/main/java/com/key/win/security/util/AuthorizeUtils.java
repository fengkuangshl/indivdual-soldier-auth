package com.key.win.security.util;

import com.key.win.common.auth.AuthenticationUtil;
import com.key.win.common.auth.detail.Authentication;
import com.key.win.common.model.system.SysMenuPermission;
import com.key.win.common.model.system.SysRole;
import com.key.win.security.service.RbacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthorizeUtils {

    private static RbacService rbacService;

    @Autowired
    public void setRbacService(RbacService rbacService) {
        this.rbacService = rbacService;
    }

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

    // @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::PAGED')")

    public static boolean hasAuthorityForQueryPaged(String authorityPrefix) {
        return rbacService.hasAuthority(getQueryPagedAuthorityExpression(authorityPrefix));
    }

    public static String getQueryPagedAuthorityExpression(String authorityPrefix) {
        return hasAuthorityExpression(authorityPrefix + "::QUERY::PAGED");
    }

    public static boolean hasAuthorityForQueryList(String authorityPrefix) {
        return rbacService.hasAuthority(getQueryListAuthorityExpression(authorityPrefix));
    }
    public static String getQueryListAuthorityExpression(String authorityPrefix) {
        return hasAuthorityExpression(authorityPrefix + "::QUERY::LIST");
    }

    public static boolean hasAuthorityForGetId(String authorityPrefix) {
        return rbacService.hasAuthority(getGetIdAuthorityExpression(authorityPrefix));
    }

    public static String getGetIdAuthorityExpression(String authorityPrefix) {
        return hasAuthorityExpression(authorityPrefix + "::QUERY::ID");
    }

    public static boolean hasAuthorityForAdd(String authorityPrefix) {
        return rbacService.hasAuthority(getAddAuthorityExpression(authorityPrefix));
    }

    public static String getAddAuthorityExpression(String authorityPrefix) {
        return hasAuthorityExpression(authorityPrefix + "::ADD");
    }

    public static boolean hasAuthorityForUpdate(String authorityPrefix) {
        return rbacService.hasAuthority(getUpdateAuthorityExpression(authorityPrefix));
    }

    public static String getUpdateAuthorityExpression(String authorityPrefix) {
        return hasAuthorityExpression(authorityPrefix + "::MODIFY");
    }

    public static boolean hasAuthorityForDelete(String authorityPrefix) {
        return rbacService.hasAuthority(getDeleteAuthorityExpression(authorityPrefix));
    }

    public static boolean hasAnyAuthority(String ...authorize) {
        return rbacService.hasAnyAuthority(authorize);
    }

    public static boolean hasRole(String ...role) {
        return rbacService.hasRole(role);
    }

    public static boolean hasAnyRole(String ...role) {
        return rbacService.hasAnyRole(role);
    }

    public static String getDeleteAuthorityExpression(String authorityPrefix) {
        return hasAuthorityExpression(authorityPrefix + "::DELETE");
    }

    public static String hasAuthorityExpression(String authority) {
        return "hasAuthority('" + authority + "')";
    }
}
