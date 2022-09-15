package com.key.win.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.key.win.basic.util.DefaultIdentifierGeneratorUtils;
import com.key.win.basic.util.FileUtils;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.file.config.ChunkFileServiceFactory;
import com.key.win.file.config.FileServiceFactory;
import com.key.win.file.dao.ChunkFileDao;
import com.key.win.file.dao.FileInfoDao;
import com.key.win.file.model.ChunkFile;
import com.key.win.file.model.FileInfo;
import com.key.win.file.service.ChunkFileService;
import com.key.win.file.service.FileInfoService;
import com.key.win.file.util.FilePropertyUtils;
import com.key.win.mybatis.page.MybatisPageServiceTemplate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public abstract class AbstractChunkFileService extends ServiceImpl<ChunkFileDao, ChunkFile> implements ChunkFileService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    protected abstract ChunkFileDao getChunkFileDao();


    protected abstract FileServiceFactory getFileServiceFactory();

    @Override
    public ChunkFile upload(ChunkFile chunkFile) throws Exception {
        upload(chunkFile.getFile().getInputStream(), chunkFile);
        return chunkFile;
    }



    private void upload(InputStream inputStream, ChunkFile fileInfo) throws Exception {
        String fileName = fileInfo.getFilename() + "-" + fileInfo.getChunkNumber();
        String filePath =  FilePropertyUtils.bizTypeCheck(fileInfo.getBizType());
        uploadFile(FileUtils.getFilePhysicalPath(filePath), fileName, inputStream);
        fileInfo.setRelativePath(FileUtils.getFileFullPath(filePath) + fileName);
        fileInfo.setPhysicalPath(FileUtils.getFilePhysicalPath(filePath) + fileName);
        getChunkFileDao().insert(fileInfo);// 将文件信息保存到数据库
        logger.info("上传文件：{}", fileInfo);
    }

    /**
     * 上传文件
     *
     * @param file
     * @param fileInfo
     */
    protected abstract void uploadFile(MultipartFile file, ChunkFile fileInfo) throws Exception;

    protected abstract String uploadFileSub(String pathName, String fileName, InputStream inputStream, boolean chunkOne) throws Exception;

    protected abstract void uploadFile(String pathName, String fileName, String originfilename) throws Exception;

    protected abstract String uploadFile(String pathName, String fileName, InputStream inputStream) throws Exception;

    protected abstract void downloadFile(String pathName, String filename, String localpath) throws Exception;


    @Override
    public void delete(ChunkFile chunkFile) {
        deleteFile(chunkFile);
        getChunkFileDao().deleteById(chunkFile.getId());
        logger.info("删除文件：{}", chunkFile);
    }

    /**
     * 删除文件资源
     *
     * @param chunkFile
     * @return
     */
    protected abstract boolean deleteFile(ChunkFile chunkFile);

    @Override
    public ChunkFile getById(String id) {
        return getChunkFileDao().selectById(id);
    }

    @Override
    public PageResult<ChunkFile> findFileInfoByPaged(PageRequest<ChunkFile> t) {
        MybatisPageServiceTemplate<ChunkFile, ChunkFile> query = new MybatisPageServiceTemplate<ChunkFile, ChunkFile>(this.baseMapper) {
            @Override
            protected AbstractWrapper constructWrapper(ChunkFile chunkFile) {
                LambdaQueryWrapper<ChunkFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                if (chunkFile != null) {
                    if (StringUtils.isNotBlank(chunkFile.getFilename())) {
                        lambdaQueryWrapper.like(ChunkFile::getFilename, chunkFile.getFilename());
                    }

                    if (StringUtils.isNotBlank(chunkFile.getMd5())) {
                        lambdaQueryWrapper.eq(ChunkFile::getMd5, chunkFile.getMd5());
                    }
                }
                return lambdaQueryWrapper;
            }
        };
        return query.doPagingQuery(t);
    }

    @Override
    public boolean saveChunk(ChunkFile chunk) {
        return super.save(chunk);
    }

    @Override
    public boolean checkChunk(String md5, Integer chunkNumber) {
        ChunkFile chunkFile = new ChunkFile();
        chunkFile.setChunkNumber(chunkNumber);
        chunkFile.setMd5(md5);
        List<ChunkFile> chunkFiles = this.findChunkFile(chunkFile);
        if (CollectionUtils.isEmpty(chunkFiles)) {
            return true;
        }
        return false;
    }

    public List<ChunkFile> findChunkFile(ChunkFile chunkFile) {
        return  super.list(this.bulidQueryWrapper(chunkFile));
    }

    private LambdaQueryWrapper bulidQueryWrapper(ChunkFile chunkFile) {
        LambdaQueryWrapper<ChunkFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (chunkFile != null) {
            if (StringUtils.isNotBlank(chunkFile.getMd5())) {
                lambdaQueryWrapper.eq(ChunkFile::getMd5, chunkFile.getMd5());
            }
            if (chunkFile.getChunkNumber() != null) {
                lambdaQueryWrapper.eq(ChunkFile::getChunkNumber, chunkFile.getChunkNumber());
            }
        }
        return lambdaQueryWrapper;
    }
}
