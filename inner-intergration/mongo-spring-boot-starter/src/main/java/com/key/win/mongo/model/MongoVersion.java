package com.key.win.mongo.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Version;

import java.io.Serializable;

public class MongoVersion implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("版本号")
    @Version
    private Integer version;
}
