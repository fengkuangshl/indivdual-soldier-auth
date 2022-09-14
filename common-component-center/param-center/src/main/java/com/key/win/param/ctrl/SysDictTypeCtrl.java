package com.key.win.param.ctrl;

import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.basic.web.Result;
import com.key.win.log.annotation.LogAnnotation;
import com.key.win.param.model.SysDictType;
import com.key.win.param.service.SysDictTypeService;
import com.key.win.security.annotation.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api("DictType相关的api")
@RequestMapping("/sysDictType")
public class SysDictTypeCtrl {
    private final static String AUTHORITY_PREFIX = "common::param::dict-type::DictType::";

    @Autowired
    private SysDictTypeService sysDictTypeService;


    @GetMapping("/get/{id}")
    @ApiOperation(value = "get")
    @LogAnnotation(module = "param-center", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::ID')")
    public Result get(@PathVariable Long id) {
        return Result.succeed(sysDictTypeService.getSysDictTypeById(id), "");
    }

    /**
     * 删除菜单
     *
     * @param id
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    @LogAnnotation(module = "param-center", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "DELETE')")
    public Result delete(@PathVariable Long id) {
        boolean b = sysDictTypeService.deleteSysDictType(id);
        return b ? Result.succeed("删除成功！") : Result.failed("删除失败");
    }

    /**
     * 添加菜单 或者 更新
     *
     * @return
     */
    @PostMapping("saveOrUpdate")
    @ApiOperation(value = "新增")
    @LogAnnotation(module = "param-center", recordRequestParam = false)
    @PreAuthorize("hasAnyAuthority('" + AUTHORITY_PREFIX + "MODIFY','" + AUTHORITY_PREFIX + "ADD')")
    public Result saveOrUpdate(@RequestBody SysDictType param) {
        boolean b = sysDictTypeService.saveOrUpdateSysDictType(param);
        return b ? Result.succeed("保存成功！") : Result.failed("保存失败");
    }

    @ApiOperation("分页")
    @LogAnnotation(module = "param-center", recordRequestParam = false)
    @PostMapping("/getSysDictTypeByPaged")
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::PAGED')")
    public PageResult<SysDictType> getSysDictTypeByPaged(@RequestBody PageRequest<SysDictType> t) {
        return sysDictTypeService.getSysDictTypeByPaged(t);
    }

    @ApiOperation("根据条件查询")
    @LogAnnotation(module = "param-center", recordRequestParam = false)
    @PostMapping("/findSysDictType")
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::LIST')")
    public Result getSysDictType(@RequestBody SysDictType sysDictType) {
        List<SysDictType> list = sysDictTypeService.findSysDictType(sysDictType);
        return Result.succeed(list, "");
    }

    @ApiOperation("根据Code查询")
    @LogAnnotation(module = "param-center", recordRequestParam = false)
    @GetMapping("/findSysDictType/{code}")
    public Result getSysDictTypeByCode(@PathVariable String code) {
        SysDictType sysDictType = sysDictTypeService.findSysDictTypeByCode(code);
        return Result.succeed(sysDictType, "");
    }
    @ApiOperation("更新状态")
    @LogAnnotation(module = "param-center", recordRequestParam = false)
    @GetMapping("/updateEnabled/{id}/{status}")
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "UPDATE::ENABLED')")
    public Result updateEnabled(@PathVariable Long id,@PathVariable Boolean status){
        return Result.succeed(sysDictTypeService.updateEnabled(id,status));
    }

}
