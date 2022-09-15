package com.key.win.file.service.impl;

import com.key.win.basic.util.FileUtils;
import com.key.win.file.config.FileServiceFactory;
import com.key.win.file.dao.ChunkFileDao;
import com.key.win.file.dao.FileInfoDao;
import com.key.win.file.model.ChunkFile;
import com.key.win.file.model.FileInfo;
import com.key.win.file.util.FilePropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service("localChunkFileServiceImpl")
public class LocalChunkFileServiceImpl extends AbstractChunkFileService {


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
    protected String uploadFileSub(String pathName, String fileName, InputStream inputStream, boolean chunkOne) throws Exception {
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
    public boolean merge(FileInfo fileInfo) {
        String filename = fileInfo.getName();
        String path = FilePropertyUtils.bizTypeCheck(fileInfo.getBizType());
        String file = FileUtils.getFilePhysicalPath(path);
        String folder = FileUtils.getChunkFilePhysicalPath(path, fileInfo.getMd5());
        FileUtils.merge(file, folder, filename);
        fileInfo.setPath(path + filename);
        fileInfo.setPhysicalPath(path + filename);
        if (fileServiceFactory.getFileService().save(fileInfo)) {
            ChunkFile chunkFile = new ChunkFile();
            chunkFile.setMd5(fileInfo.getMd5());
            List<ChunkFile> chunkFiles = super.findChunkFile(chunkFile);
            if (!CollectionUtils.isEmpty(chunkFiles)) {
                boolean b = super.removeByIds(chunkFiles.stream().map(ChunkFile::getId).collect(Collectors.toSet()));
                return b;
            }
        }
        return false;
    }
}
