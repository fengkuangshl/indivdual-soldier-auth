package com.key.win.auth.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.key.win.auth.customer.dao.CustomerInfoDao;
import com.key.win.auth.customer.dao.CustomerInfoVoDao;
import com.key.win.auth.customer.model.CustomerInfo;
import com.key.win.auth.customer.service.CustomerInfoService;
import com.key.win.auth.customer.vo.CustomerInfoVo;
import com.key.win.basic.exception.BizException;
import com.key.win.basic.util.BeanUtils;
import com.key.win.basic.util.DateUtils;
import com.key.win.basic.util.DefaultIdentifierGeneratorUtils;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.common.auth.AuthenticationUtil;
import com.key.win.mybatis.page.MybatisPageServiceTemplate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CustomerInfoServiceImpl extends ServiceImpl<CustomerInfoDao, CustomerInfo> implements CustomerInfoService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String CUSTOMER_SEQUENCE_PREFIX = "CNO.";

    @Autowired
    private CustomerInfoVoDao customerInfoVoDao;

    @Override
    public PageResult<CustomerInfoVo> findCustomerByPaged(PageRequest<CustomerInfoVo> t) {
        MybatisPageServiceTemplate<CustomerInfoVo, CustomerInfoVo> query = new MybatisPageServiceTemplate<CustomerInfoVo, CustomerInfoVo>(customerInfoVoDao) {
            @Override
            protected AbstractWrapper constructWrapper(CustomerInfoVo customer) {
                return buildCustomerInfoLambdaQueryWrapper(customer);
            }
            //SELECT dci.*, (select count(1) FROM device_auth da where da.auth_Code = dci.auth_Code) as authorized_quantity   from device_customer_info dci

            protected String constructNativeSql() {
                return "SELECT * From (SELECT dci.*, (select count(1) FROM device_auth da where da.auth_Code = dci.auth_device_code) as authorized_quantity   from device_customer_info dci where dci.enable_flag = 1)  TMP";
            }

        };
        return query.doPagingQuery(t);
    }

    @Override
    public List<CustomerInfo> findCustomer(CustomerInfoVo customer) {
        LambdaQueryWrapper<CustomerInfo> lambdaQueryWrapper = buildCustomerInfoLambdaQueryWrapper(customer);
        return this.list(lambdaQueryWrapper);
    }

    private LambdaQueryWrapper<CustomerInfo> buildCustomerInfoLambdaQueryWrapper(CustomerInfoVo customer) {
        LambdaQueryWrapper<CustomerInfo> lambdaQueryWrapper = new LambdaQueryWrapper<CustomerInfo>();
        if (customer != null) {
            if (StringUtils.isNotBlank(customer.getSequence())) {
                lambdaQueryWrapper.eq(CustomerInfo::getSequence, customer.getSequence());
            }
            if (StringUtils.isNotBlank(customer.getAuthDeviceCode())) {
                lambdaQueryWrapper.eq(CustomerInfo::getAuthDeviceCode, customer.getAuthDeviceCode());
            }
            if (StringUtils.isNotBlank(customer.getCompanyName())) {
                lambdaQueryWrapper.like(CustomerInfo::getCompanyName, customer.getCompanyName());
            }
            if (StringUtils.isNotBlank(customer.getCompanyPhone())) {
                lambdaQueryWrapper.eq(CustomerInfo::getCompanyPhone, customer.getCompanyPhone());
            }
            if (StringUtils.isNotBlank(customer.getLeadMobile())) {
                lambdaQueryWrapper.eq(CustomerInfo::getLeadMobile, customer.getLeadMobile());
            }
            if (StringUtils.isNotBlank(customer.getLeadName())) {
                lambdaQueryWrapper.like(CustomerInfo::getLeadName, customer.getLeadName());
            }
            if (StringUtils.isNotBlank(customer.getProjectName())) {
                lambdaQueryWrapper.like(CustomerInfo::getProjectName, customer.getProjectName());
            }
            if (StringUtils.isNotBlank(customer.getProjectNo())) {
                lambdaQueryWrapper.eq(CustomerInfo::getProjectNo, customer.getProjectNo());
            }
            if (StringUtils.isNotBlank(customer.getStartNum()) && StringUtils.isNotBlank(customer.getEndNum())) {
                lambdaQueryWrapper.between(CustomerInfo::getAuthDeviceNum, Integer.valueOf(customer.getStartNum()), Integer.valueOf(customer.getEndNum()));
            }

            if (StringUtils.isNotBlank(customer.getStartDate()) && StringUtils.isNotBlank(customer.getEndDate())) {
                lambdaQueryWrapper.between(CustomerInfo::getExpireDeviceDate, DateUtils.strToTime(customer.getStartDate()), DateUtils.strToTime(customer.getEndDate()));
            }
        }
        return lambdaQueryWrapper;
    }

    @Override
    public boolean saveOrUpdateCustomer(CustomerInfo customer) {
        CustomerInfo po = null;
        if (customer.getId() != null) {
            po = super.getById(customer.getId());
            if (po == null) {
                logger.error("[{}]的客户信息不存在！", customer.getId());
                throw new BizException("客户信息不存在!");
            }
            if (!po.getSequence().equals(customer.getSequence())) {
                logger.error("客户编号[{}]已锁定，不允许修改！", customer.getSequence());
                throw new BizException("客户编号已锁定，不允许修改！!");
            }
            if (!po.getAuthDeviceCode().equals(customer.getAuthDeviceCode())) {
                logger.error("客户授权码[{}]已锁定，不允许修改！", customer.getAuthDeviceCode());
                throw new BizException("客户授权码已锁定，不允许修改！!");
            }
            BeanUtils.copyPropertiesToPartField(customer, po);
            LambdaQueryWrapper<CustomerInfo> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
            objectLambdaQueryWrapper.eq(CustomerInfo::getId,po.getId());
            objectLambdaQueryWrapper.eq(CustomerInfo::getCreateUserId,po.getCreateUserId());
            objectLambdaQueryWrapper.eq(CustomerInfo::getAuthDeviceCode,po.getAuthDeviceCode());
            return super.update(po,objectLambdaQueryWrapper);
            //return super.updateById(po);
        } else {
            po = customer;
            po.setSequence(CUSTOMER_SEQUENCE_PREFIX + DefaultIdentifierGeneratorUtils.getGeneratorLongId());
            CustomerInfo existCustomer = findCustomerByAuthCode(customer.getAuthDeviceCode());
            if (existCustomer != null) {
                logger.error("客户授权码[{}]已存在！", customer.getAuthDeviceCode());
                throw new BizException("客户授权码已存在，不允许使用！!");
            }
            boolean save = super.save(po);
            return  save;
        }

    }

    public CustomerInfo findCustomerByAuthCode(String authCode) {
        CustomerInfoVo customer = new CustomerInfoVo();
        customer.setAuthDeviceCode(authCode);
        List<CustomerInfo> customerInfos = this.findCustomer(customer);
        if (!CollectionUtils.isEmpty(customerInfos)) {
            return customerInfos.get(0);
        }
        return null;
    }
}
