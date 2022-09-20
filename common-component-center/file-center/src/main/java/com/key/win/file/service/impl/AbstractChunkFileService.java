package com.key.win.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.key.win.basic.util.FileUtils;
import com.key.win.basic.util.IndividualSoldierAuthConstantUtils;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.file.config.FileServiceFactory;
import com.key.win.file.dao.ChunkFileDao;
import com.key.win.file.model.ChunkFile;
import com.key.win.file.model.FileInfo;
import com.key.win.file.service.ChunkFileService;
import com.key.win.file.util.FilePropertyUtils;
import com.key.win.mybatis.page.MybatisPageServiceTemplate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractChunkFileService extends ServiceImpl<ChunkFileDao, ChunkFile> implements ChunkFileService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String REDIS_CHUNK_FILE_COUNT_KEY_PREFIX = IndividualSoldierAuthConstantUtils.REDIS_ROOT_KEY_PREFIX + "chun:file:count:";

    protected abstract ChunkFileDao getChunkFileDao();


    protected abstract FileServiceFactory getFileServiceFactory();

    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    @Override
    public ChunkFile upload(ChunkFile chunkFile) throws Exception {
        upload(chunkFile.getFile().getInputStream(), chunkFile);
        return chunkFile;
    }


    private void upload(InputStream inputStream, ChunkFile fileInfo) throws Exception {
        String key = REDIS_CHUNK_FILE_COUNT_KEY_PREFIX + fileInfo.getIdentifier();
        Long increment = redisTemplate.opsForValue().increment(key, 1);
        String chunkFileName = fileInfo.getFilename() + "-" + fileInfo.getChunkNumber();
        String filePath = FilePropertyUtils.bizTypeCheck(fileInfo.getBizType());
        String chunkFilePhysicalPath = FileUtils.getChunkFilePhysicalPath(filePath, fileInfo.getIdentifier());
        fileInfo.setRelativePath(FileUtils.getChunkFileFullPath(filePath, fileInfo.getIdentifier()) + chunkFileName);
        fileInfo.setPhysicalPath(chunkFilePhysicalPath);
        fileInfo.setChunkFileName(chunkFileName);
        getChunkFileDao().insert(fileInfo);// 将文件信息保存到数据库
        uploadFileSub(fileInfo, inputStream, increment.longValue() == fileInfo.getTotalChunks().longValue());
        if (increment.intValue() == fileInfo.getTotalChunks().longValue()) {
            redisTemplate.delete(key);
        }
        logger.info("上传文件：{}", fileInfo);
    }

    /**
     * 上传文件
     *
     * @param file
     * @param fileInfo
     */
    protected abstract void uploadFile(MultipartFile file, ChunkFile fileInfo) throws Exception;

    protected abstract String uploadFileSub(ChunkFile file, InputStream inputStream, boolean chunkOne) throws Exception;

    protected abstract void uploadFile(String pathName, String fileName, String originFilename) throws Exception;

    protected abstract String uploadFile(String pathName, String fileName, InputStream inputStream) throws Exception;

    protected abstract void downloadFile(String pathName, String filename, String localpath) throws Exception;

    protected abstract void mergeFile(String targetFile, String folder, ChunkFile filename) throws Exception ;


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
                    if (StringUtils.isNotBlank(chunkFile.getChunkFileName())) {
                        lambdaQueryWrapper.like(ChunkFile::getChunkFileName, chunkFile.getChunkFileName());
                    }

                    if (StringUtils.isNotBlank(chunkFile.getIdentifier())) {
                        lambdaQueryWrapper.eq(ChunkFile::getIdentifier, chunkFile.getIdentifier());
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
    public boolean checkChunk(String identifier, Long chunkNumber) {
        ChunkFile chunkFile = new ChunkFile();
        chunkFile.setChunkNumber(chunkNumber);
        chunkFile.setIdentifier(identifier);
        List<ChunkFile> chunkFiles = this.findChunkFile(chunkFile);
        if (CollectionUtils.isEmpty(chunkFiles)) {
            return true;
        }
        return false;
    }

    public List<ChunkFile> findChunkFile(ChunkFile chunkFile) {
        return super.list(this.buildQueryWrapper(chunkFile));
    }

    public LambdaQueryWrapper buildQueryWrapper(ChunkFile chunkFile) {
        LambdaQueryWrapper<ChunkFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (chunkFile != null) {
            if (StringUtils.isNotBlank(chunkFile.getIdentifier())) {
                lambdaQueryWrapper.eq(ChunkFile::getIdentifier, chunkFile.getIdentifier());
            }
            if (chunkFile.getChunkNumber() != null) {
                lambdaQueryWrapper.eq(ChunkFile::getChunkNumber, chunkFile.getChunkNumber());
            }
        }
        return lambdaQueryWrapper;
    }

    @Override
    public boolean merge(ChunkFile fileInfo) throws Exception  {
        String filename = fileInfo.getFilename();
        String path = FilePropertyUtils.bizTypeCheck(fileInfo.getBizType());
        String filePhysicalPath = FileUtils.getFilePhysicalPath(path);
        String folder = FileUtils.getChunkFilePhysicalPath(path, fileInfo.getIdentifier());

        mergeFile(filePhysicalPath + filename, folder, fileInfo);
        FileInfo file = new FileInfo();
        file.setPath(FileUtils.getFileFullPath(path) + filename);
        file.setPhysicalPath(FileUtils.getFilePhysicalPath(path) + filename);
        file.setBizType(fileInfo.getBizType());
        file.setContentType(fileInfo.getFileType());
        file.setMd5(fileInfo.getIdentifier());
        file.setName(filename);
        file.setSize(fileInfo.getTotalSize());
        if (getFileServiceFactory().getFileService().save(file)) {
            ChunkFile chunkFile = new ChunkFile();
            chunkFile.setIdentifier(fileInfo.getIdentifier());
            List<ChunkFile> chunkFiles = this.findChunkFile(chunkFile);
            if (!CollectionUtils.isEmpty(chunkFiles)) {
                boolean b = super.removeByIds(chunkFiles.stream().map(ChunkFile::getId).collect(Collectors.toSet()));
                return b;
            }
        }
        return false;
    }
}
