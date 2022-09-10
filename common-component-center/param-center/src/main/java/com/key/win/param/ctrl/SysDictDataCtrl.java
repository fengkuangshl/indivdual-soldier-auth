package com.key.win.param.ctrl;

import com.key.win.basic.exception.BizException;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.basic.web.Result;
import com.key.win.log.annotation.LogAnnotation;
import com.key.win.param.model.SysDictData;
import com.key.win.param.service.SysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api("DictData相关的api")
@RequestMapping("/sysDictData")
public class SysDictDataCtrl {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
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
    @DeleteMapping("/delete/{id}")
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
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "新增")
    @LogAnnotation(module = "param-center", recordRequestParam = false)
    public Result saveOrUpdate(@RequestBody SysDictData param) {
        if(param==null){
            logger.error("数据字典为空！！");
            throw  new BizException("数据字典信息为空");
        }
        if(param.getType()==null || param.getType() == -1L){
            logger.error("数据字典类型为空！！");
            throw  new BizException("数据字典类型为空");
        }
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

    @ApiOperation("更新状态")
    @LogAnnotation(module = "param-center", recordRequestParam = false)
    @GetMapping("/updateEnabled/{id}/{status}")
    public Result updateEnabled(@PathVariable Long id,@PathVariable Boolean status){
        return Result.succeed(sysDictDataService.updateEnabled(id,status));
    }
}
