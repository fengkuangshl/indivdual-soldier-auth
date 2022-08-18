package com.key.win.system.ctrl;

import com.key.win.basic.config.MyPasswordEncoder;
import com.key.win.basic.exception.BizException;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.basic.web.Result;
import com.key.win.common.auth.AuthenticationUtil;
import com.key.win.common.auth.detail.Authentication;
import com.key.win.common.model.system.SysUser;
import com.key.win.log.annotation.LogAnnotation;
import com.key.win.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/*")
@Api("User相关的api")
public class SysUserCtrl {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MyPasswordEncoder myPasswordEncoder;


    @PostMapping("/findSysUserByPaged")
    @ApiOperation(value = "User分页")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public PageResult<SysUser> findSysUserByPaged(@RequestBody PageRequest<SysUser> pageRequest) {
        return sysUserService.findSysUserByPaged(pageRequest);
    }


    @GetMapping("/getUserAll")
    @ApiOperation(value = "获取所有用户")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result getUserAll() {
        return Result.succeed(sysUserService.list());
    }


    @GetMapping("/getAllUserAndState")
    @ApiOperation(value = "获取所有用户及状态")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result getAllUserAndState() throws Exception {
        List<SysUser> allSysUser = AuthenticationUtil.getSysUsersAndState(sysUserService.list());
        return Result.succeed(allSysUser);
    }

    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    @LogAnnotation(module = "system", recordRequestParam = true)
    public Result saveSysUser(@RequestBody SysUser sysUser) {
        if (StringUtils.isBlank(sysUser.getUserName())) {
            logger.error("用户名为空!");
            throw new BizException("用户名为空!");
        }
        if (StringUtils.isBlank(sysUser.getPassword())) {
            logger.error("密码为空!");
            throw new BizException("密码为空");
        }
        boolean b = sysUserService.saveSysUser(sysUser);
        return Result.result(b);
    }

    @PostMapping("/updateSysUser")
    @ApiOperation(value = "用户更新")
    @LogAnnotation(module = "system", recordRequestParam = true)
    public Result updateSysUser(@RequestBody SysUser sysUser) {
        boolean b = sysUserService.updateSysUser(sysUser);
        return Result.result(b);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取用户")
    public Result get(@PathVariable Long id) {
        SysUser byId = sysUserService.getById(id);
        return Result.succeed(byId);
    }

    @GetMapping("/getUserFullById/{id}")
    @ApiOperation(value = "获取用户及自己的机构、设备、角色信息")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result getUserFullById(@PathVariable Long id) {
        SysUser byId = sysUserService.getUserFullById(id);
        return Result.succeed(byId);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    @LogAnnotation(module = "system", recordRequestParam = true)
    public Result delete(@PathVariable Long id) {
        boolean b = sysUserService.deleteById(id);
        return Result.result(b);
    }

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    @LogAnnotation(module = "system", recordRequestParam = true)
    public Result login(@RequestBody SysUser sysUser, HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        if (sysUser == null) {
            logger.error("user is null !!!");
            throw new BizException("用户或密码为空！");
        }
        if (StringUtils.isBlank(sysUser.getUserName())) {
            logger.error("username is null !!!");
            throw new BizException("用户或密码为空！");
        }
        if (StringUtils.isBlank(sysUser.getPassword())) {
            logger.error("password is null !!!");
            throw new BizException("用户或密码为空！");
        }
        Map<String, Object> token = sysUserService.login(sysUser, userAgent);
        return Result.succeed(token, "登录成功！");
    }

    @GetMapping("/refresh/{token}")
    @ApiOperation(value = "refresh")
    @LogAnnotation(module = "system", recordRequestParam = true)
    public Result refreshToken(@PathVariable String token, HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        try {
            Map<String, Object> res = sysUserService.refreshToken(token, userAgent);
            return Result.succeed(res, "刷新成功！");
        } catch (Exception e) {
            logger.error("refreshToken error:{}", e.getMessage(), e);
            return Result.failed(401, e.getMessage());
        }
    }

    @GetMapping("/current")
    @ApiOperation(value = "获取当前登录用户")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result getLoginAppUser() {
        return Result.succeed(AuthenticationUtil.getAuthentication());
    }

    @GetMapping("/logout")
    @ApiOperation(value = "登出当前登录用户")
    @LogAnnotation(module = "system", recordRequestParam = true)
    public Result logout() {
        Authentication authentication = AuthenticationUtil.getAuthentication();
        if (authentication != null) {
            authentication.setOnLine(false);
        }
        return AuthenticationUtil.logout() ? Result.succeed("操作成功") : Result.succeed("操作失败");
    }

    @GetMapping("/resetPassword/{id}")
    @ApiOperation(value = "登录用户密码")
    @LogAnnotation(module = "system", recordRequestParam = true)
    public Result resetPassword(@PathVariable Long id) {
        if (id == null) {
            logger.error("id为空！");
            throw new BizException("id为空！");
        }
        boolean b = sysUserService.resetPassword(id);
        return Result.result(b);
    }

    @PostMapping("/modifyMyPassword")
    @ApiOperation(value = "修改自己的密码")
    @LogAnnotation(module = "system", recordRequestParam = true)
    public Result modifyMyPassword(@RequestBody SysUser sysUser) {
        Authentication loginApp = AuthenticationUtil.getAuthentication();
        if (sysUser.getId() == null || sysUser.getId() != loginApp.getId()) {
            logger.error("非法操作！");
            throw new BizException("非法操作！");
        }
        if (StringUtils.isBlank(sysUser.getPassword())) {
            logger.error("原密码为空！");
            throw new BizException("原密码为空！");
        }
        if (StringUtils.isBlank(sysUser.getNewPassword())) {
            logger.error("新密码为空！");
            throw new BizException("新密码为空！");
        }
        if (sysUser.getPassword().equals(sysUser.getNewPassword())) {
            logger.error("新密码不能和原密码一样！");
            throw new BizException("新密码不能和原密码一样！");
        }
        sysUser.setId(loginApp.getId());
        boolean b = sysUserService.modifyPassword(sysUser);
        return Result.result(b);
    }

    @PostMapping("/granted")
    @ApiOperation(value = "组分配用户")
    @LogAnnotation(module = "system", recordRequestParam = true)
    public Result setUserToGroup(@RequestBody SysUser sysUser) {
        if (CollectionUtils.isEmpty(sysUser.getUserIds())) {
            logger.error("授权用户为空！");
            throw new BizException("授权用户为空！");
        }
        if (sysUser.getGroupId() == null) {
            logger.error("组信息为空！");
            throw new BizException("组信息为空！");
        }
        sysUserService.setUserToGroup(sysUser.getGroupId(), sysUser.getUserIds());
        return Result.succeed("角色分配菜单成功");
    }

    @GetMapping("/getOnLineUser")
    @ApiOperation(value = "获所有在线用户")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result getOnLineUser() throws Exception {
        return Result.succeed(AuthenticationUtil.getOnLineUser());
    }

    @GetMapping("/getLoginApp")
    @ApiOperation(value = "获所当前用户")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result getLoginApp() throws Exception {
        Map<String, Object> userInfo = new HashMap<>();
        Authentication authentication = AuthenticationUtil.getAuthentication();
        userInfo.put("user", authentication);
        userInfo.put("permissions", authentication.getPermissions());
        return Result.succeed(userInfo);
    }

    /**
     * 新增or更新
     *
     * @param sysUser
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result saveOrUpdate(@RequestBody SysUser sysUser) {
        if (sysUser != null) {
            if (sysUser.getId() != null) {
                boolean b = sysUserService.updateSysUser(sysUser);
                return Result.result(b);
            } else {

                boolean b = sysUserService.saveSysUser(sysUser);
                return Result.result(b);
            }
        }

        return Result.failed("用户信息为空！");
    }

    /**
     * 修改用户状态
     *
     * @return
     * @author
     */
    @ApiOperation(value = "修改用户状态")
    @PostMapping("/updateEnabled")
    @LogAnnotation(module = "user-center", recordRequestParam = false)
    public Result updateEnabled(@RequestBody SysUser sysUser) {
        boolean b = sysUserService.updateEnabled(sysUser);
        return Result.result(b);
    }
}
