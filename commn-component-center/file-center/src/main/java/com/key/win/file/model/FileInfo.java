package com.key.win.file.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.key.win.basic.util.AccessPathUtils;
import com.key.win.common.model.basic.MybatisID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.regex.Matcher;

@Data
@TableName("sys_file_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel("附件实体")
public class FileInfo extends MybatisID {
    @ApiModelProperty("文件的MD5")
    private String md5;
    //  原始文件名
    @ApiModelProperty("原始文件名")
    private String name;
    //	上传文件类型
    @ApiModelProperty("上传文件类型")
    private String contentType;
    //	文件大小
    @ApiModelProperty("size")
    private long size;
    // 文件服务器保存的物理全路径地址
    @ApiModelProperty("物理全路径地址")
    private String physicalPath;

    // 文件路径
    @ApiModelProperty("文件路径")
    private String path;

    @ApiModelProperty("文件业务类型")
    private String bizType;

    // 文件后缀
    @ApiModelProperty("文件后缀")
    @TableField(exist = false)
    private String fileSuffix;

    public String getFileSuffix() {
        if (StringUtils.isNotBlank(this.fileSuffix)) {
            return fileSuffix;
        }
        return name.substring(name.indexOf(".") + 1);
    }

    public String getAccessPath() {
        if (path.startsWith("http://") || path.startsWith("https://")) {
            return path.replaceAll(Matcher.quoteReplacement(File.separator), "/");
        }
        String accessPathPrefix = AccessPathUtils.getAccessPathPrefix();
        if (StringUtils.isNotBlank(accessPathPrefix)) {
            return accessPathPrefix + path.replaceAll(Matcher.quoteReplacement(File.separator), "/");
        }
        return null;
    }

    public String getPhysicalPathProcess() {
        if (StringUtils.isNotBlank(physicalPath)) {
            return physicalPath.replaceAll(Matcher.quoteReplacement(File.separator), "/");
        }
        return physicalPath;
    }

    public String getPathProcess() {
        if (StringUtils.isNotBlank(path)) {
            return path.replaceAll(Matcher.quoteReplacement(File.separator), "/");
        }
        return path;
    }
}
