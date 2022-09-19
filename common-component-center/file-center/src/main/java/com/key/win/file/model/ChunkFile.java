package com.key.win.file.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.key.win.common.model.basic.MybatisID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;


@Data
@TableName("sys_chunk_file")
@EqualsAndHashCode(callSuper = true)
@ApiModel("分片文件实体")
public class ChunkFile extends MybatisID {
    /**
     * 当前文件块，从1开始
     */
    @ApiModelProperty("当前文件块，从1开始")
    private Long chunkNumber;
    /**
     * 分块大小
     */
    @ApiModelProperty("分块大小")
    private Long chunkSize;
    /**
     * 当前分块大小
     */
    @ApiModelProperty("当前分块大小")
    private Long currentChunkSize;
    /**
     * 总大小
     */
    @ApiModelProperty("总大小")
    private Long totalSize;
    /**
     * 文件标识
     */
    @ApiModelProperty("文件标识")
    private String identifier;
    /**
     * 文件名
     */
    @ApiModelProperty("分片文件名")
    private String chunkFileName;


    /**
     * 相对路径
     */
    @ApiModelProperty("相对路径")
    private String relativePath;

    // 文件服务器保存的物理全路径地址
    @ApiModelProperty("物理全路径地址")
    private String physicalPath;
    /**
     * 总块数
     */
    @ApiModelProperty("总块数")
    private Long totalChunks;
    /**
     * 文件类型
     */
    @ApiModelProperty("文件类型")
    private String bizType;
    /**
     * 上传文件类型
     */
    @ApiModelProperty("上传文件类型")
    private String fileType;

    @ApiModelProperty("上传文件")
    @TableField(exist = false)
    private MultipartFile file;

    @ApiModelProperty("文件名")
    @TableField(exist = false)
    private String filename;


}
