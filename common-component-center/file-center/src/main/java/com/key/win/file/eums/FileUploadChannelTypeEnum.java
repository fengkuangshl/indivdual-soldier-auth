package com.key.win.file.eums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import org.apache.commons.lang3.StringUtils;

public enum FileUploadChannelTypeEnum {
    FTP(0, "FTP"),
    LOCAL(1, "本地");
    @EnumValue
    private int code;
    private String text;

    private FileUploadChannelTypeEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }


    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public static FileUploadChannelTypeEnum getFileUploadChannelTypeEnumByName(String name) {
        if (StringUtils.isNotBlank(name)) {
            for (FileUploadChannelTypeEnum channelType : FileUploadChannelTypeEnum.values()) {
                if (channelType.name().toLowerCase().equals(name.toLowerCase())) {
                    return channelType;
                }
            }
        }
        return null;
    }

    public static FileUploadChannelTypeEnum getFileUploadChannelType(String name) {
        FileUploadChannelTypeEnum fileUploadChannelTypeEnumByName = getFileUploadChannelTypeEnumByName(name);
        if (fileUploadChannelTypeEnumByName != null) {
            return fileUploadChannelTypeEnumByName;
        }
        return FileUploadChannelTypeEnum.LOCAL;
    }
}
