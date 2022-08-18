package com.key.win.mongo.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@EqualsAndHashCode(callSuper = false)
public class MongoID extends MongoCommonField {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("Id")
    @MongoId(FieldType.OBJECT_ID)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
