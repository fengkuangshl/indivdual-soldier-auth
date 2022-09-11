package com.key.win.param.ctrl;

import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.basic.web.Result;
import com.key.win.log.annotation.LogAnnotation;
import com.key.win.param.model.SysDictTree;
import com.key.win.param.service.SysDictTreeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sysDictTree")
@Api("DictTree相关的api")
public class SysDictTreeCtrl {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SysDictTreeService sysDictTreeService;

    @PostMapping("/findSysDictTreeByPaged")
    @ApiOperation(value = "DictTree分页")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public PageResult<SysDictTree> findSysDictTreeByPaged(@RequestBody PageRequest<SysDictTree> t) {
        return sysDictTreeService.findSysDictTreeByPaged(t);
    }

    @PostMapping("/findChildrenNodeByParentId")
    @ApiOperation(value = "获取当前树节点的所有孩子节点&分页")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public PageResult<SysDictTree> findChildrenNodeByParentId(@RequestBody PageRequest<SysDictTree> t) {
        return sysDictTreeService.findChildrenNodeByParentId(t);
    }

    @PostMapping("/findParentNodeByParentId")
    @ApiOperation(value = "获取当前树节点的所有父节点&分页")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public PageResult<SysDictTree> findParentNodeByParentId(@RequestBody PageRequest<SysDictTree> t) {
        return sysDictTreeService.findParentNodeByParentId(t);
    }


    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取DictTree")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result get(@PathVariable String id) {
        return Result.succeed(sysDictTreeService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    @LogAnnotation(module = "system", recordRequestParam = true)
    public Result delete(@PathVariable Long id) {
        boolean b = sysDictTreeService.deleteById(id);
        return Result.result(b);
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "新增/更新")
    @LogAnnotation(module = "system", recordRequestParam = true)
    public Result saveOrUpdate(@RequestBody SysDictTree sysDictTree) {
        if (StringUtils.isBlank(sysDictTree.getValue())) {
            logger.error("value为空！");
            throw new IllegalArgumentException("value为空！");
        }
        if (StringUtils.isBlank(sysDictTree.getLabel())) {
            logger.error("名称为空！");
            throw new IllegalArgumentException("名称为空！");
        }
        if (sysDictTree.getParentId() == null) {
            logger.error("parent Id is null !");
            throw new IllegalArgumentException("父节点为空！");
        }
        if (sysDictTree.getType() == null) {
            logger.error("type  is null !");
            throw new IllegalArgumentException("字典类型信息为空！");
        }
        boolean b = sysDictTreeService.saveOrUpdateDictTree(sysDictTree);
        return Result.result(b);
    }

//    @GetMapping("/getDictTreeAll")
//    @ApiOperation(value = "所有树节点的信息信息")
//    @LogAnnotation(module = "system", recordRequestParam = false)
//    public Result getDictTreeAll() {
//        return Result.succeed(sysDictTreeService.list());
//    }

    @GetMapping("/getDictTreeByParenId/{parentId}")
    @ApiOperation(value = "根据父节点获取所有孩子节点")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result getDictTreeByParenId(@PathVariable Long parentId) {
        return Result.succeed(sysDictTreeService.findSysDictTreeByParentId(parentId));
    }

    @GetMapping("/getDictTree/{type}")
    @ApiOperation(value = "获取机构树")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result getDictTree(@PathVariable Long type) {
        return Result.succeed(sysDictTreeService.getDictTree(type));
    }

    @GetMapping("/findLeafNode/{type}")
    @ApiOperation(value = "获取所有叶节点")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result getLeafNode(@PathVariable Long type) {
        return Result.succeed(sysDictTreeService.findLeafNode(type));
    }

    @LogAnnotation(module = "param-center", recordRequestParam = false)
    @GetMapping("/updateEnabled/{id}/{status}")
    public Result updateEnabled(@PathVariable Long id,@PathVariable Boolean status){
        return Result.succeed(sysDictTreeService.updateEnabled(id,status));
    }
}
