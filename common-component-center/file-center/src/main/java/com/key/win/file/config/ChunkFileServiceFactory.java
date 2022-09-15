package com.key.win.file.config;

import com.key.win.file.eums.FileUploadChannelTypeEnum;
import com.key.win.file.service.ChunkFileService;
import com.key.win.file.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ChunkFileServiceFactory {

    private Map<FileUploadChannelTypeEnum, ChunkFileService> map = new HashMap<>();


    @Autowired
    private ChunkFileService ftpChunkFileServiceImpl;

    @Autowired
    private ChunkFileService localChunkFileServiceImpl;

    @Value("${spring.file.upload.channel}")
    private String fileUploadChannel;


    @PostConstruct
    public void init() {
        map.put(FileUploadChannelTypeEnum.FTP, ftpChunkFileServiceImpl);
        map.put(FileUploadChannelTypeEnum.LOCAL, localChunkFileServiceImpl);
    }

    public ChunkFileService getFileService() {
        return map.get(FileUploadChannelTypeEnum.getFileUploadChannelType(fileUploadChannel));
    }
}
