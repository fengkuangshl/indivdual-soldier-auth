package param.ctrl;

import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.basic.web.Result;
import com.key.win.log.annotation.LogAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import param.model.SysDictType;
import param.service.SysDictTypeService;

import java.util.List;


@RestController
@Api("DictType相关的api")
@RequestMapping("/sysDictType")
public class SysDictTypeCtrl {
    @Autowired
    private SysDictTypeService sysDictTypeService;

    @GetMapping("/get/{id}")
    @ApiOperation(value = "get")
    @LogAnnotation(module = "param-center", recordRequestParam = false)
    public Result get(@PathVariable Long id) {
        return Result.succeed(sysDictTypeService.getSysDictTypeById(id), "");
    }

    /**
     * 删除菜单
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    @LogAnnotation(module = "param-center", recordRequestParam = false)
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
    public Result saveOrUpdate(@RequestBody SysDictType param) {
        boolean b = sysDictTypeService.saveOrUpdateSysDictType(param);
        return b ? Result.succeed("保存成功！") : Result.failed("保存失败");
    }

    @ApiOperation("分页")
    @LogAnnotation(module = "param-center", recordRequestParam = false)
    @PostMapping("/getSysDictTypeByPaged")
    public PageResult<SysDictType> getSysDictTypeByPaged(@RequestBody PageRequest<SysDictType> t) {
        return sysDictTypeService.getSysDictTypeByPaged(t);
    }

    @ApiOperation("根据条件查询")
    @LogAnnotation(module = "param-center", recordRequestParam = false)
    @PostMapping("/findSysDictType")
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
}
