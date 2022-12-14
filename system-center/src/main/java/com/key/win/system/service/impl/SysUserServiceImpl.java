package com.key.win.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.key.win.basic.config.MyPasswordEncoder;
import com.key.win.basic.exception.BizException;
import com.key.win.basic.exception.UserIllegalException;
import com.key.win.basic.util.BeanUtils;
import com.key.win.basic.util.IndividualSoldierAuthConstantUtils;
import com.key.win.basic.util.UUIDUtils;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.common.auth.AuthenticationUtil;
import com.key.win.common.auth.detail.Authentication;
import com.key.win.common.enums.UserTypeEnum;
import com.key.win.common.model.system.*;
import com.key.win.common.vo.RefreshTokenVo;
import com.key.win.mybatis.page.MybatisPageServiceTemplate;
import com.key.win.system.dao.*;
import com.key.win.system.exception.AccountDisabledException;
import com.key.win.system.exception.AccountException;
import com.key.win.system.exception.AccountNotFoundException;
import com.key.win.system.exception.BadCredentialsException;
import com.key.win.system.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private MyPasswordEncoder myPasswordEncoder;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Autowired
    private SysUserGroupDao sysUserGroupDao;

    @Autowired
    private SysRoleMenuPermissionService sysRoleMenuPermissionService;

    @Autowired
    private SysMenuPermissionService sysMenuPermissionService;

    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysPermissionService sysPermissionService;

    @Override
    public boolean updateSysUser(SysUser sysUser) {
        if (sysUser.getId() == null) {
            logger.error("??????id?????????");
            throw new IllegalArgumentException("??????id?????????!");
        }
        SysUser updateUser = this.getById(sysUser.getId());
        if (updateUser == null) {
            logger.error("???????????????");
            throw new IllegalArgumentException("???????????????!");
        }
        if (StringUtils.isNotBlank(sysUser.getUserName()) && !updateUser.getUserName().equals(sysUser.getUserName())) {
            logger.error("?????????????????????{}?????????{}", updateUser.getUserName(), sysUser.getUserName());
            throw new IllegalArgumentException("??????????????????");
        }
        setRoleToUser(sysUser);
        setGroupToUser(sysUser);

        updateUser.setNickName(sysUser.getNickName());
        updateUser.setPhone(sysUser.getPhone());
        updateUser.setType(sysUser.getType());
        updateUser.setSex(sysUser.getSex());
        updateUser.setHeadImgUrl(sysUser.getHeadImgUrl());
        return this.saveOrUpdate(updateUser);
    }

    private void setGroupToUser(SysUser sysUser) {
        sysUserGroupDao.deleteUserGroup(sysUser.getId(), null);
        if (!CollectionUtils.isEmpty(sysUser.getGroupIds())) {
            sysUserGroupDao.saveBatchUserIdAndGroupIds(sysUser.getId(), sysUser.getGroupIds());
        }
    }

    private void setRoleToUser(SysUser sysUser) {
        sysUserRoleDao.deleteUserRole(sysUser.getId(), null);
        if (!CollectionUtils.isEmpty(sysUser.getRoleIds())) {
            sysUserRoleDao.saveBatchUserIdAndRoleIds(sysUser.getId(), sysUser.getRoleIds());
        }
    }

    @Override
    public PageResult<SysUser> findSysUserByPaged(PageRequest<SysUser> t) {
        MybatisPageServiceTemplate<SysUser, SysUser> mybatiesPageServiceTemplate = new MybatisPageServiceTemplate<SysUser, SysUser>(this.baseMapper) {
            @Override
            protected AbstractWrapper constructWrapper(SysUser sysUser) {
                LambdaQueryWrapper<SysUser> lqw = new LambdaQueryWrapper<SysUser>();
                if (sysUser != null) {
                    if (StringUtils.isNotBlank(sysUser.getNickName())) {
                        lqw.like(SysUser::getNickName, sysUser.getNickName());
                    }
                    if (StringUtils.isNotBlank(sysUser.getUserName())) {
                        lqw.like(SysUser::getUserName, sysUser.getUserName());
                    }
                }
                return lqw;
            }
        };
        return mybatiesPageServiceTemplate.doPagingQuery(t);
    }

    public List<SysUser> findSysUserByUserName(String userName) {
        LambdaQueryWrapper<SysUser> lqw = new LambdaQueryWrapper<SysUser>();
        lqw.eq(SysUser::getUserName, userName);
        List<SysUser> list = this.list(lqw);
        return list;
    }

    @Override
    public Map<String, Object> refreshToken(String token, String userAgent) {
        RefreshTokenVo refreshTokenVo = AuthenticationUtil.getRefreshTokenToRedis(token);
        logger.info("??????[{}]??????token", token);
        if (refreshTokenVo == null) {
            logger.error("refreshToken is null !!");
            throw new UserIllegalException("refreshToken??????");
        }
        if (StringUtils.isBlank(refreshTokenVo.getUserName())) {
            logger.error("refreshToken??????userName is null !!");
            throw new UserIllegalException("refreshToken??????");
        }
        if (StringUtils.isBlank(refreshTokenVo.getToken())) {
            logger.error("refreshToken??????token is null !!");
            throw new UserIllegalException("refreshToken??????");
        }
        logger.info("??????[{}]????????????[token:{}][userName:{}]", refreshTokenVo.getUserName(), refreshTokenVo.getToken());
        List<SysUser> list = this.findSysUserByUserName(refreshTokenVo.getUserName());
        SysUser dbUser = list.get(0);
        checkIsEnabled(dbUser);
        Authentication loginUser = new Authentication();
        setUserExtInfo(userAgent, dbUser, loginUser);

        /**
         * ??????refreshtoken
         */
        if (AuthenticationUtil.isIsRefreshSelf()) {
            AuthenticationUtil.deleteRefreshTokenToRedis(token);
            //get new refresh token
            token = UUIDUtils.getGUID();
        }
        /**
         * ???????????????????????????????????????token
         * ??????????????????????????????
         */
        Authentication authentication = AuthenticationUtil.getAuthenticationToRedis(refreshTokenVo.getToken());
        if (authentication != null) {
            AuthenticationUtil.deleteTokenToRedis(refreshTokenVo.getToken());
        }

        return createToken(loginUser, token);
    }

    private void checkIsEnabled(SysUser dbUser) {
        if (!dbUser.getEnabled()) {
            logger.error("??????{}???????????????", dbUser.getUserName());
            throw new AccountDisabledException("????????????????????????");
        }
    }

    @Override
    public Map<String, Object> login(SysUser sysUser, String userAgent) {
        List<SysUser> list = this.findSysUserByUserName(sysUser.getUserName());
        if (list == null || list.size() == 0) {
            logger.error("{}??????????????????", sysUser.getUserName());
            throw new AccountNotFoundException("???????????????????????????");
        }
        if (list.size() > 1) {
            logger.error("{}????????????{}???", sysUser.getUserName(), list.size());
            throw new AccountException("???????????????,?????????????????????");
        }

        SysUser dbUser = list.get(0);
        String encode = myPasswordEncoder.encode(sysUser.getPassword());
        if (!encode.equals(dbUser.getPassword())) {
            logger.error("??????{}???????????????", dbUser.getUserName());
            throw new BadCredentialsException("???????????????????????????");
        }
        checkIsEnabled(dbUser);
        Authentication loginUser = new Authentication();
        setUserExtInfo(userAgent, dbUser, loginUser);
        String refreshToken = UUIDUtils.getGUID();
        return createToken(loginUser, refreshToken);
    }

    private Map<String, Object> createToken(Authentication loginUser, String refreshToken) {
        String guid = UUIDUtils.getGUID();
        loginUser.setToken(guid);
        loginUser.setOnLine(true);
        loginUser.setRefreshToken(refreshToken);
        // loginUser.setDataPermissionVo(this.getCurrentUserDataPermissions(loginUser));
        //????????????
        AuthenticationUtil.setAuthenticationToRedis(loginUser);
        AuthenticationUtil.setRefreshTokenToRedis(refreshToken, loginUser);
        return this.getUserToken(guid, refreshToken);
    }

    private void setUserExtInfo(String userAgent, SysUser dbUser, Authentication loginUser) {
        BeanUtils.copyProperties(dbUser, loginUser);
        List<SysGroup> groupByUserId = sysUserGroupDao.findGroupByUserId(dbUser.getId());
        List<SysRole> rolesByUserId = sysUserRoleDao.findRolesByUserId(dbUser.getId());
        loginUser.setSysGroups(groupByUserId);
        loginUser.setSysRoles(rolesByUserId);
        if (dbUser.getType() == UserTypeEnum.ADMIN) {
            //List<SysMenuPermission> permissionDaoByRoleIds = sysMenuPermissionService.list();
            List<SysMenu> menus = sysMenuService.list();
            List<SysPermission> sysPermissions = sysPermissionService.list();
            loginUser.setPermissions(getMenuPermissions(menus, sysPermissions));
            loginUser.setMenus(menus);
        } else if (!CollectionUtils.isEmpty(rolesByUserId)) {
            Set<Long> roleIds = rolesByUserId.stream().map(SysRole::getId).collect(Collectors.toSet());
            List<SysRoleMenuPermission> grantMenus = sysRoleMenuPermissionService.findGrantMenus(roleIds);
            Set<Long> menuIds = grantMenus.stream().map(SysRoleMenuPermission::getMenuId).collect(Collectors.toSet());
            List<SysMenu> menus = sysMenuService.findSysMenuByMenuIds(menuIds);
            List<SysRoleMenuPermission> grantMenuPermissions = sysRoleMenuPermissionService.findGrantMenuPermissions(roleIds);
            Set<Long> menuPermissionIds = grantMenuPermissions.stream().map(SysRoleMenuPermission::getMenuPermissionId).collect(Collectors.toSet());
            List<SysMenuPermission> sysMenuPermissionByIds = sysMenuPermissionService.findSysMenuPermissionByIds(menuPermissionIds);
            loginUser.setPermissions(sysMenuPermissionByIds);
            loginUser.setMenus(menus);
        }
        Collections.sort(loginUser.getMenus(), new Comparator<SysMenu>() {
            @Override
            public int compare(SysMenu o1, SysMenu o2) {
                return o1.getSort() - o2.getSort();
            }
        });
        setLoginType(loginUser, userAgent);
    }

    private List<SysMenuPermission> getMenuPermissions(List<SysMenu> menus, List<SysPermission> sysPermissions) {
        List<SysMenuPermission> sysMenuPermissions = new ArrayList<>();
        for (SysMenu menu : menus) {
            if (menu.getParentId() != -1) {
                for (SysPermission sysPermission : sysPermissions) {
                    SysMenuPermission sysMenuPermission = new SysMenuPermission();
                    sysMenuPermission.setChecked(Boolean.TRUE);
                    sysMenuPermission.setPermissionCode(menu.getPath().replaceAll("/", "::") +"::"+ sysPermission.getPermission());
                    sysMenuPermission.setMenuId(menu.getId());
                    sysMenuPermission.setPermissionId(sysPermission.getId());
                    sysMenuPermissions.add(sysMenuPermission);
                }
            }
        }
        if(CollectionUtils.isEmpty(sysMenuPermissions)){
            SysMenuPermission sysMenuPermission = new SysMenuPermission();
            sysMenuPermission.setChecked(Boolean.TRUE);
            sysMenuPermission.setPermissionCode("*::*::*");
            sysMenuPermissions.add(sysMenuPermission);
        }
        return sysMenuPermissions;
    }

    private void setLoginType(Authentication loginUser, String info) {
        if (info.contains("Windows")) {
            loginUser.setLoginType("Windows");
        } else if (info.contains("Macintosh")) {
            loginUser.setLoginType("Macintosh");
        } else if (info.contains("Android")) {
            loginUser.setLoginType("Android");
        } else if (info.contains("iPhone")) {
            loginUser.setLoginType("iPhone");
        } else if (info.contains("iPad")) {
            loginUser.setLoginType("iPad");
        } else {
            loginUser.setLoginType("Other");
        }
    }

    public Map<String, Object> getUserToken(String token, String refreshToken) {
        Map<String, Object> userTokenVo = new HashMap<>();
        userTokenVo.put(IndividualSoldierAuthConstantUtils.REQUEST_TOKEN_KEY, token);
        userTokenVo.put(IndividualSoldierAuthConstantUtils.TOKEN_EXPIRES_IN_KEY, AuthenticationUtil.getTokenExpires());
        userTokenVo.put(IndividualSoldierAuthConstantUtils.TOKEN_BEARER_KEY, IndividualSoldierAuthConstantUtils.TOKEN_BEARER_VAUE);
        userTokenVo.put(IndividualSoldierAuthConstantUtils.REFRESH_TOKEN_KEY, refreshToken);
        return userTokenVo;
    }

    @Override
    public boolean saveSysUser(SysUser sysUser) {
        List<SysUser> existUsers = this.findSysUserByUserName(sysUser.getUserName());
        if (!CollectionUtils.isEmpty(existUsers)) {
            logger.error("{}??????????????????!", sysUser.getUserName());
            throw new BizException("?????????????????????");
        }
        sysUser.setPassword(myPasswordEncoder.encode(IndividualSoldierAuthConstantUtils.RESET_PASSWORD));
        boolean b = this.saveOrUpdate(sysUser);
        setRoleToUser(sysUser);
        return b;
    }

    @Override
    public boolean modifyPassword(SysUser sysUser) {
        SysUser user = this.getById(sysUser.getId());
        if (user == null) {
            logger.error("id???{}?????????????????????????????????", sysUser.getId());
            throw new IllegalArgumentException("??????????????????");
        }
        user.setPassword(myPasswordEncoder.encode(sysUser.getNewPassword()));
        return this.saveOrUpdate(user);
    }

    @Override
    public boolean resetPassword(Long id) {
        SysUser user = this.getById(id);
        if (user == null) {
            logger.error("id???{}?????????????????????????????????", id);
            throw new BizException("??????????????????");
        }
        user.setPassword(myPasswordEncoder.encode(IndividualSoldierAuthConstantUtils.RESET_PASSWORD));
        return this.saveOrUpdate(user);
    }

    @Override
    public void setUserToGroup(Long groupId, Set<Long> userIds) {
        sysUserGroupDao.deleteUserGroup(null, groupId);
        if (!CollectionUtils.isEmpty(userIds)) {
            sysUserGroupDao.saveBatchUserIdsAndGroupId(userIds, groupId);
        }
    }

    @Override
    public SysUser getUserFullById(Long id) {
        SysUser byId = this.getById(id);
        byId.setSysRoles(sysUserRoleDao.findRolesByUserId(id));
        byId.setSysGroups(sysUserGroupDao.findGroupByUserId(id));

        if (!CollectionUtils.isEmpty(byId.getSysGroups())) {
            Set<Long> collect = byId.getSysGroups().stream().map(SysGroup::getId).collect(Collectors.toSet());
            byId.setGroupIds(collect);

        }
        if (!CollectionUtils.isEmpty(byId.getSysRoles())) {
            Set<Long> collect = byId.getSysRoles().stream().map(SysRole::getId).collect(Collectors.toSet());
            byId.setRoleIds(collect);
        }
        return byId;
    }

    @Override
    public boolean deleteById(Long id) {
        SysUser userFullById = this.getUserFullById(id);
        List<SysRole> sysRoles = userFullById.getSysRoles();
        List<SysGroup> sysGroups = userFullById.getSysGroups();
        if (!CollectionUtils.isEmpty(sysRoles)) {
            logger.error("????????????[{}]?????????????????????SysRole?????????", id);
            throw new BizException("????????????????????????????????????");
        }
        if (!CollectionUtils.isEmpty(sysGroups)) {
            logger.error("????????????[{}]?????????????????????SysGroup?????????", id);
            throw new BizException("???????????????????????????????????????");
        }
        return this.removeById(id);
    }

    @Override
    public boolean updateEnabled(SysUser sysUser) {
        if (sysUser.getId() == null) {
            logger.error("??????id?????????");
            throw new IllegalArgumentException("??????id?????????!");
        }
        SysUser appUser = this.getById(sysUser.getId());
        if (appUser == null) {
            logger.error("??????????????????");
            throw new BizException("??????????????????");
        }
        appUser.setEnabled(sysUser.getEnabled());

        return this.saveOrUpdate(appUser);
    }
}
