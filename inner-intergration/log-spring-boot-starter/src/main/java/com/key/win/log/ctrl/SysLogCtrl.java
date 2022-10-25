package com.key.win.log.ctrl;

import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.basic.web.Result;
import com.key.win.log.annotation.LogAnnotation;
import com.key.win.log.model.SysLog;
import com.key.win.log.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/log/*")
@Api("SysLog相关的api")
public class SysLogCtrl {
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SysLogService sysLogService;

    @PostMapping("/findSysLogByPaged")
    @ApiOperation(value = "SysLog分页")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public PageResult<SysLog> findSysLogByPaged(@RequestBody PageRequest<SysLog> pageRequest) {
        return sysLogService.findSysLogByPaged(pageRequest);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取SysLog")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result get(@PathVariable String id) {
        return Result.succeed(sysLogService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result delete(@PathVariable String id) {
        boolean b = sysLogService.removeById(id);
        return Result.result(b);
    }

    @GetMapping("/getSysLogAll")
    @ApiOperation(value = "获取所有SysLog")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result getSysLogAll() {
        return Result.succeed(sysLogService.list());
    }
}
