package com.key.win.auth.customer.vo;

import com.key.win.auth.customer.model.CustomerInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("客户实体Vo")
public class CustomerInfoVo extends CustomerInfo {
    @ApiModelProperty("已授权数量")
    private long authorizedQuantity;

    @ApiModelProperty("查询条件的开始时间")
    private String startDate;
    @ApiModelProperty("查询条件的结束时间")
    private String endDate;

    @ApiModelProperty("查询条件的开始数量")
    private Integer startNum;
    @ApiModelProperty("查询条件的结束数量")
    private Integer endNum;
}
