package com.key.win.auth.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.key.win.auth.customer.model.CustomerInfo;
import com.key.win.auth.customer.vo.CustomerInfoVo;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;

import java.util.List;

public interface CustomerInfoService extends IService<CustomerInfo> {

    PageResult<CustomerInfoVo> findCustomerByPaged(PageRequest<CustomerInfoVo> t);

    List<CustomerInfo> findCustomer(CustomerInfoVo customer);

    CustomerInfo findCustomerByAuthCode(String code);

    boolean saveOrUpdateCustomer(CustomerInfo customer);

}
