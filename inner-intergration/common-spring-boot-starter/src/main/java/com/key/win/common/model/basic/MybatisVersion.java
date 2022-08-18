package com.key.win.common.model.basic;

import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MybatisVersion implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("版本号")
    @Version
    private Integer version = 0;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
