package com.key.win.basic.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7320421323343652634L;
    @ApiModelProperty("当前页")
    private int pageNo = 1;
    @ApiModelProperty("每页条数")
    private int pageSize = 10;
    @ApiModelProperty("排序字段")
    private String sortName = "";
    @ApiModelProperty("排序方向[ ASC-升序, DESC-降序 ]")
    private OrderDir sortDir = OrderDir.DESC;
    @ApiModelProperty("对应实体对象")
    private T t;

    @ApiModelProperty("每页条数")
    public int getPageSize() {
        return (0 == pageSize) ? 10 : pageSize;
    }

    @ApiModelProperty("获取条数")
    public int getFirstResult() {
        return (pageNo - 1) * pageSize;
    }


}
