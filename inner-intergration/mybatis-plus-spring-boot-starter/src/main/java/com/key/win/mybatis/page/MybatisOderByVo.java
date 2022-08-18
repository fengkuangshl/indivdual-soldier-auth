package com.key.win.mybatis.page;

import com.key.win.basic.web.OrderDir;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel("Mybatis排序VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MybatisOderByVo implements Serializable {

    private String sortName;
    private OrderDir sortDir;


}
