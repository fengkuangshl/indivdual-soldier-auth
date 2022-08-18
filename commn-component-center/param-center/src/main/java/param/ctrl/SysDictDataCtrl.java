package param.ctrl;

import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.basic.web.Result;
import com.key.win.log.annotation.LogAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import param.model.SysDictData;
import param.service.SysDictDataService;

import java.util.List;


@RestController
@Api("DictData相关的api")
@RequestMapping("/sysDictData")
public class SysDictDataCtrl {
    @Autowired
    private SysDictDataService sysDictDataService;

    @GetMapping("/get/{id}")
    @ApiOperation(value = "get")
    @LogAnnotation(module = "param-center", recordRequestParam = false)
    public Result get(@PathVariable Long id) {
        return Result.succeed(sysDictDataService.getSysDictDataById(id), "");
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
        boolean b = sysDictDataService.deleteSysDictData(id);
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
    public Result saveOrUpdate(@RequestBody SysDictData param) {
        boolean b = sysDictDataService.saveOrUpdateSysDictData(param);
        return b ? Result.succeed("保存成功！") : Result.failed("保存失败");
    }

    @ApiOperation("分页")
    @LogAnnotation(module = "param-center", recordRequestParam = false)
    @PostMapping("/getSysDictDataByPaged")
    public PageResult<SysDictData> getSysDictDataByPaged(@RequestBody PageRequest<SysDictData> t) {
        return sysDictDataService.getSysDictDataByPaged(t);
    }

    @ApiOperation("根据条件查询")
    @LogAnnotation(module = "param-center", recordRequestParam = false)
    @PostMapping("/findSysDictData")
    public Result getSysDictData(@RequestBody SysDictData sysDictData) {
        List<SysDictData> list = sysDictDataService.findSysDictData(sysDictData);
        return Result.succeed(list, "");
    }

    @ApiOperation("根据Type查询")
    @LogAnnotation(module = "param-center", recordRequestParam = false)
    @GetMapping("/findSysDictData/{type}")
    public Result getSysDictDataByType(@PathVariable Long type) {
        List<SysDictData> list = sysDictDataService.findSysDictDataByType(type);
        return Result.succeed(list, "");
    }
}
