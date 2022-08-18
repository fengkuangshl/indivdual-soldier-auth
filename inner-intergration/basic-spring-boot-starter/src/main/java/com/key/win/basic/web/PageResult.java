package com.key.win.basic.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> extends BaseResult {
    @ApiModelProperty("当前页数")
    @Builder.Default
    private int pageNo = 1;
    @ApiModelProperty("每页条数")
    @Builder.Default
    private int pageSize = 10;
    @ApiModelProperty("总条数")
    @Builder.Default
    private long count = 0;

    //总页数
    //private int					totalPage;

    @ApiModelProperty("分页数据列表")
    private List<T> data = new ArrayList<T>();

    @ApiModelProperty("总页数数")
    public long getTotalPage() {
        return (count + pageSize - 1) / pageSize;
    }


}
