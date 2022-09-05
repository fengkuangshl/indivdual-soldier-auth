package com.key.win.auth.customer.ctrl;


import com.key.win.auth.customer.model.CustomerInfo;
import com.key.win.auth.customer.service.CustomerInfoService;
import com.key.win.auth.customer.vo.CustomerInfoVo;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.basic.web.Result;
import com.key.win.datalog.annotation.DataLog;
import com.key.win.log.annotation.LogAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/*")
@Api("客户相关的api")
public class CustomerInfoCtrl {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CustomerInfoService customerInfoService;

    @PostMapping("/findCustomerInfoByPaged")
    @ApiOperation(value = "客户信息分页")
    @LogAnnotation(module = "device-auth", recordRequestParam = false)
    public PageResult<CustomerInfoVo> findCustomerInfoByPaged(@RequestBody PageRequest<CustomerInfoVo> t) {
        return customerInfoService.findCustomerByPaged(t);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取客户信息")
    @LogAnnotation(module = "device-auth", recordRequestParam = false)
    public Result get(@PathVariable Long id) {
        return Result.succeed(customerInfoService.getById(id));
    }

    @DataLog
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    @LogAnnotation(module = "device-auth", recordRequestParam = true)
    public Result delete(@PathVariable Long id) {
        boolean b = customerInfoService.removeById(id);
        return Result.result(b);
    }

    @DataLog
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "新增/更新")
    @LogAnnotation(module = "device-auth", recordRequestParam = true)
    public Result saveOrUpdate(@RequestBody CustomerInfo customerInfo) {
        if (StringUtils.isBlank(customerInfo.getAuthDeviceCode())) {
            logger.error("授权码为空！");
            throw new IllegalArgumentException("授权码为空！");
        }
        if (StringUtils.isBlank(customerInfo.getCompanyName())) {
            logger.error("客户名称为空！");
            throw new IllegalArgumentException("客户名称为空！");
        }
        if (StringUtils.isBlank(customerInfo.getProjectNo())) {
            logger.error("项目号称为空！");
            throw new IllegalArgumentException("项目号称为空！");
        }
        if (customerInfo.getAuthDeviceNum() == null) {
            logger.error("授权设备数量为空！");
            throw new IllegalArgumentException("授权设备数量为空！");
        }
        if (customerInfo.getIsVerify() && customerInfo.getExpireDeviceDate() == null) {
            logger.error("客户设备授权到期日为空！");
            throw new IllegalArgumentException("客户设备授权到期日为空！");
        }
        return Result.result(customerInfoService.saveOrUpdateCustomer(customerInfo));
    }

    @GetMapping("/getCustomerInfoAll")
    @ApiOperation(value = "所有机构信息")
    @LogAnnotation(module = "device-auth", recordRequestParam = false)
    public Result getCustomerInfoAll() {
        return Result.succeed(customerInfoService.list());
    }
}
