package com.key.win.system.ctrl;


import com.key.win.basic.web.*;
import com.key.win.common.model.system.SysGroup;
import com.key.win.log.annotation.LogAnnotation;
import com.key.win.system.service.SysGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group/*")
@Api("Group相关的api")
public class SysGroupCtrl {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SysGroupService sysGroupService;

    @PostMapping("/findSysGroupByPaged")
    @ApiOperation(value = "Group分页")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public PageResult<SysGroup> findSysGroupByPaged(@RequestBody PageRequest<SysGroup> t) {
        return sysGroupService.findSysGroupByPaged(t);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取Group")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result get(@PathVariable Long id) {
        return Result.succeed(sysGroupService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    @LogAnnotation(module = "system", recordRequestParam = true)
    public Result delete(@PathVariable Long id) {
        boolean b = sysGroupService.deleteById(id);
        return Result.result(b);
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "新增/更新")
    @LogAnnotation(module = "system", recordRequestParam = true)
    public Result saveOrUpdate(@RequestBody SysGroup sysGroup) {
        if (StringUtils.isBlank(sysGroup.getCode())) {
            logger.error("机构code为空！");
            throw new IllegalArgumentException("机构code为空！");
        }
        if (StringUtils.isBlank(sysGroup.getName())) {
            logger.error("机构名称为空！");
            throw new IllegalArgumentException("机构名称为空！");
        }
        if (sysGroup.getParentId() == null) {
            logger.error("parent Id is null !");
            throw new IllegalArgumentException("父节点为空！");
        }
        boolean b = sysGroupService.saveOrUpdateGroup(sysGroup);
        return Result.result(b);
    }

    @GetMapping("/getGroupAll")
    @ApiOperation(value = "所有机构信息")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result getGroupAll() {
        return Result.succeed(sysGroupService.list());
    }

    @GetMapping("/getGroupByParenId/{parentId}")
    @ApiOperation(value = "根据父节点获取所有孩子节点")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result getGroupByParenId(@PathVariable Long parentId) {
        return Result.succeed(sysGroupService.findSysGroupByParentId(parentId));
    }

    @GetMapping("/getGroupTree")
    @ApiOperation(value = "获取机构树")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result getGroupTree() {
        return Result.succeed(sysGroupService.getGroupTree());
    }

    @GetMapping("/findLeafNode")
    @ApiOperation(value = "获取所有叶节点")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result getLeafNode() {
        return Result.succeed(sysGroupService.findLeafNode());
    }
}
