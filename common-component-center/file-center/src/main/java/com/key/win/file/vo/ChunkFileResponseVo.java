package com.key.win.file.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("断点续传文件上传时检查输出的VO")
@Data
public class ChunkFileResponseVo {
    @ApiModelProperty("resourceId")
    private Long resourceId;
    @ApiModelProperty("上传类型")
    private String contentType;
    @ApiModelProperty("是否为秒传")
    private boolean skipUpload;
    @ApiModelProperty("原始文件名")
    private String title;
    @ApiModelProperty("访问路径")
    private String accessPath;
    @ApiModelProperty("已上传的分片信息")
    private List<Long> uploaded;
    @ApiModelProperty("是否需要合并")
    private boolean needMerge;

}
