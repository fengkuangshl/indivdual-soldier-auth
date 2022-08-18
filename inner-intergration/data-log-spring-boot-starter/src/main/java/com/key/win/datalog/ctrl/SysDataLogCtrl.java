package com.key.win.datalog.ctrl;

import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.basic.web.Result;
import com.key.win.datalog.model.SysDataLog;
import com.key.win.datalog.service.SysDataLogService;
import com.key.win.log.annotation.LogAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data/log/*")
@Api("DataLog相关的api")
public class SysDataLogCtrl {
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SysDataLogService dataLogService;

    @PostMapping("/findDataLogByPaged")
    @ApiOperation(value = "SysDataLog分页")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public PageResult<SysDataLog> findDataLogByPaged(@RequestBody PageRequest<SysDataLog> pageRequest) {
        return dataLogService.findSysLogByPaged(pageRequest);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取SysDataLog")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result get(@PathVariable String id) {
        return Result.succeed(dataLogService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    @LogAnnotation(module = "system", recordRequestParam = true)
    public Result delete(@PathVariable String id) {
        boolean b = dataLogService.removeById(id);
        return Result.result(b);
    }

    @GetMapping("/getSysDataLogAll")
    @ApiOperation(value = "获取所有SysDataLog")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result getSysDataLogAll() {
        return Result.succeed(dataLogService.list());
    }

}
