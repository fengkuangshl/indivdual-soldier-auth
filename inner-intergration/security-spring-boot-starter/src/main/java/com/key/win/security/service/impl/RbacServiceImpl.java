package com.key.win.security.service.impl;

import com.key.win.common.auth.AuthenticationUtil;
import com.key.win.common.auth.detail.Authentication;
import com.key.win.common.model.system.SysMenuPermission;
import com.key.win.common.model.system.SysPermission;
import com.key.win.common.model.system.SysRole;
import com.key.win.security.service.RbacService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service("rbacService")
public class RbacServiceImpl implements RbacService {

    private  final Logger logger = LoggerFactory.getLogger(this.getClass());

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean permitAll() {
        return true;
    }

    @Override
    public boolean denyAll() {
        return false;
    }

    @Override
    public boolean anonymous() {
        return AuthenticationUtil.isAnonymousUser();
    }

    @Override
    public boolean authenticated() {
        return !AuthenticationUtil.isAnonymousUser();
    }

    @Override
    public boolean hasRole(String... roles) {
        if (roles != null && roles.length > 0) {
            Map<String, SysRole> sysRoleMap = this.getRoleByCurrentUser();
            if (!CollectionUtils.isEmpty(sysRoleMap)) {
                for (String code : roles) {
                    if (sysRoleMap.get(code) == null) {
                        logger.error("{}角色为空", code);
                        return false;
                    }
                }
                logger.info("角色校验通过！");
                return true;
            }
        } else {
            logger.error("角色为空");
        }
        logger.error("角色不校验通过！");
        return false;
    }

    @Override
    public boolean hasAnyRole(String... roles) {
        if (roles != null && roles.length > 0) {
            Map<String, SysRole> sysRoleMap = this.getRoleByCurrentUser();
            if (!CollectionUtils.isEmpty(sysRoleMap)) {
                for (String code : roles) {
                    if (sysRoleMap.get(code) != null) {
                        logger.info("{}角色校验通过！", code);
                        return true;
                    }
                }
            }
        } else {
            logger.error("角色为空！");
        }
        logger.error("角色不校验通过！");
        return false;
    }

    @Override
    public boolean hasAuthority(String... authoritys) {
        if (authoritys != null && authoritys.length > 0) {
            Set<String> permissionSet = this.getPermissionByCurrentUser();
            if (!CollectionUtils.isEmpty(permissionSet)) {
                for (String authority : authoritys) {
                    for (String permission : permissionSet) {
                        if (!antPathMatcher.match(permission, authority)) {
                            logger.error("{}权限校验不通过！", authority);
                            return false;
                        }
                    }
                }
                logger.info("权限校验通过！");
                return true;
            }
        } else {
            logger.error("权限为空！");
        }
        logger.error("权限不校验通过！");
        return false;
    }

    @Override
    public boolean hasAnyAuthority(String... authoritys) {
        if (authoritys != null && authoritys.length > 0) {
            Set<String> permissionSet = this.getPermissionByCurrentUser();
            if (!CollectionUtils.isEmpty(permissionSet)) {
                for (String authority : authoritys) {
                    for (String permission : permissionSet) {
                        if (antPathMatcher.match(permission, authority)) {
                            logger.error("{}权限校验通过！", authority);
                            return true;
                        }
                    }
                }
            }
        } else {
            logger.error("权限为空！");
        }
        logger.error("权限不校验通过！");
        return false;
    }

    public static void main(String[] args) {
        RbacServiceImpl l = new RbacServiceImpl();
        l.hasRole("");
    }

    public Map<String, SysRole> getRoleByCurrentUser() {
        Authentication authentication = AuthenticationUtil.getAuthentication();
        if (authentication != null) {
            List<SysRole> sysRoles = authentication.getSysRoles();
            if (!CollectionUtils.isEmpty(sysRoles)) {
                return sysRoles.stream().collect(Collectors.toMap(SysRole::getCode, a -> a, (k1, k2) -> k1));
            }
        }
        return null;
    }

    public Set<String> getPermissionByCurrentUser() {
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
