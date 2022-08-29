package com.key.win.security.service.impl;

import com.key.win.common.auth.AuthenticationUtil;
import com.key.win.common.model.system.SysRole;
import com.key.win.security.service.RbacService;
import com.key.win.security.util.AuthorizeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service("rbacService")
public class RbacServiceImpl implements RbacService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
            Map<String, SysRole> sysRoleMap = AuthorizeUtils.getRole();
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
            Map<String, SysRole> sysRoleMap = AuthorizeUtils.getRole();
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
    public boolean hasAuthority(String... authorize) {
        if (authorize != null && authorize.length > 0) {
            Set<String> permissionSet = AuthorizeUtils.getPermission();
            if (!CollectionUtils.isEmpty(permissionSet)) {
                List<String> verifiedPermissions = Stream.of(authorize).collect(Collectors.toList());
                for (String authority : authorize) {
                    for (String permission : permissionSet) {
                        if (antPathMatcher.match(permission, authority)) {
                            logger.info("{}权限校验通过！", authority);
                            verifiedPermissions.remove(authority);
                        }
                    }
                }
                if (verifiedPermissions.size() > 0) {
                    verifiedPermissions.forEach(vp -> {
                        logger.error("{}权限校验不通过！", vp);
                    });
                    return false;
                } else {
                    return true;
                }

            }
        } else {
            logger.error("权限为空！");
        }
        logger.error("权限不校验通过！");
        return false;
    }

    @Override
    public boolean hasAnyAuthority(String... authorize) {
        if (authorize != null && authorize.length > 0) {
            Set<String> permissionSet = AuthorizeUtils.getPermission();
            if (!CollectionUtils.isEmpty(permissionSet)) {
                for (String authority : authorize) {
                    for (String permission : permissionSet) {
                        if (antPathMatcher.match(permission, authority)) {
                            logger.info("{}权限校验通过！", authority);
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
}
