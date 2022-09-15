package com.key.win.file.service.impl;

import com.key.win.file.config.FileServiceFactory;
import com.key.win.file.dao.ChunkFileDao;
import com.key.win.file.dao.FileInfoDao;
import com.key.win.file.model.ChunkFile;
import com.key.win.file.model.FileInfo;
import com.key.win.file.util.FtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service("ftpChunkFileServiceImpl")
public class FtpChunkFileServiceImpl extends AbstractChunkFileService {


    @Autowired
    private ChunkFileDao chunkFileDao;

    @Autowired
    private FileServiceFactory fileServiceFactory;

    @Autowired
    private FtpUtils ftpUtils;


    @Override
    protected FileServiceFactory getFileServiceFactory() {
        return fileServiceFactory;
    }

    @Override
    public ChunkFileDao getChunkFileDao() {
        return chunkFileDao;
    }

    @Override
    protected void uploadFile(MultipartFile file, ChunkFile fileInfo) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected String uploadFileSub(String pathName, String fileName, InputStream inputStream, boolean chunkOne) throws Exception {
        return ftpUtils.uploadFileSub(pathName, fileName, inputStream, chunkOne);
    }

    @Override
    protected boolean deleteFile(ChunkFile fileInfo) {
        // TODO Auto-generated method stub
        return ftpUtils.deleteFile(fileInfo.getPhysicalPath());
    }

    @Override
    protected void uploadFile(String pathName, String fileName, String originfilename) throws Exception {
        // TODO Auto-generated method stub
        ftpUtils.uploadFile(pathName, fileName, originfilename);
    }

    @Override
    protected String uploadFile(String pathName, String fileName, InputStream inputStream) throws Exception {
        // TODO Auto-generated method stub
        return ftpUtils.uploadFile(pathName, fileName, inputStream);
    }

    @Override
    protected void downloadFile(String pathName, String filename, String localpath) throws Exception {
        // TODO Auto-generated method stub
        ftpUtils.downloadFile(pathName, filename, localpath);
    }

    @Override
    public boolean merge(FileInfo fileInfo) {
        return false;
    }
}
