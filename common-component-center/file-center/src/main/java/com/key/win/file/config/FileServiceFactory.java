package com.key.win.file.config;

import com.key.win.file.eums.FileUploadChannelTypeEnum;
import com.key.win.file.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class FileServiceFactory {

    private Map<FileUploadChannelTypeEnum, FileInfoService> map = new HashMap<>();


    @Autowired
    private FileInfoService ftpServiceImpl;

    @Autowired
    private FileInfoService localFileServiceImpl;

    @Value("${spring.file.upload.channel}")
    private String fileUploadChannel;


    @PostConstruct
    public void init() {
        map.put(FileUploadChannelTypeEnum.FTP, ftpServiceImpl);
        map.put(FileUploadChannelTypeEnum.LOCAL, localFileServiceImpl);
    }

    public FileInfoService getFileService() {
        return map.get(FileUploadChannelTypeEnum.getFileUploadChannelType(fileUploadChannel));
    }
}
