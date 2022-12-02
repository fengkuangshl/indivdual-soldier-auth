package com.key.win.file.service.impl;

import com.key.win.basic.util.FileUtils;
import com.key.win.datasource.annotation.DataSource;
import com.key.win.file.config.FileServiceFactory;
import com.key.win.file.dao.ChunkFileDao;
import com.key.win.file.dao.FileInfoDao;
import com.key.win.file.model.ChunkFile;
import com.key.win.file.model.FileInfo;
import com.key.win.file.util.FilePropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("localChunkFileServiceImpl")
@DataSource(name = "file")
public class LocalChunkFileServiceImpl extends AbstractChunkFileService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ChunkFileDao chunkFileDao;

    @Autowired
    private FileServiceFactory fileServiceFactory;

    @Override
    public ChunkFileDao getChunkFileDao() {
        return chunkFileDao;
    }

    @Override
    protected FileServiceFactory getFileServiceFactory() {
        return fileServiceFactory;
    }

    @Override
    protected void uploadFile(MultipartFile file, ChunkFile fileInfo) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected String uploadFileSub(ChunkFile file, InputStream inputStream, boolean lastUpload) throws Exception {
        FileUtils.uploadFile(inputStream, file.getPhysicalPath(),file.getChunkFileName() );
        if (lastUpload) {
            logger.info("最后一次分片结束，进行合成！");
            //FileUtils.merge(FileUtils.getFilePhysicalPath(filePath), FileUtils.getChunkFileFullPath(filePath, fileInfo.getIdentifier()), fileInfo.getFilename());chunkOne
            super.merge(file);

        }
        return null;
    }

    @Override
    protected boolean deleteFile(ChunkFile fileInfo) {
        // TODO Auto-generated method stub
        return FileUtils.deleteFile(fileInfo.getPhysicalPath());
    }

    @Override
    protected void uploadFile(String pathName, String fileName, String originfilename) throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    protected String uploadFile(String pathName, String fileName, InputStream inputStream) throws Exception {
        // TODO Auto-generated method stub
        FileUtils.uploadFile(inputStream, pathName, fileName);
        return null;
    }

    @Override
    protected void downloadFile(String pathName, String filename, String localpath) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void mergeFile(String targetFile, String folder, ChunkFile chunkFile) throws Exception  {
        FileUtils.merge(targetFile, folder, chunkFile.getFilename());
    }
}
