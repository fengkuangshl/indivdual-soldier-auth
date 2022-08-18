package com.key.win.file.service.impl;

import com.key.win.basic.util.FileUtils;
import com.key.win.file.dao.FileInfoDao;
import com.key.win.file.model.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service("localFileServiceImpl")
public class LocalFileServiceImpl extends AbstractFileInfoService {


    @Autowired
    private FileInfoDao fileInfoDao;

    @Override
    protected FileInfoDao getFileDao() {
        return this.fileInfoDao;
    }

    @Override
    protected void uploadFile(MultipartFile file, FileInfo fileInfo) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected String uploadFileSub(String pathName, String fileName, InputStream inputStream, boolean chunkOne) throws Exception {
        return null;
    }

    @Override
    protected boolean deleteFile(FileInfo fileInfo) {
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

}
