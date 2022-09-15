package com.key.win.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.key.win.basic.util.AccessPathUtils;
import com.key.win.basic.util.DefaultIdentifierGeneratorUtils;
import com.key.win.basic.util.FileUtils;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.file.dao.FileInfoDao;
import com.key.win.file.model.FileInfo;
import com.key.win.file.service.FileInfoService;
import com.key.win.mybatis.page.MybatisPageServiceTemplate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.Calendar;
import java.util.regex.Matcher;

public abstract class AbstractFileInfoService extends ServiceImpl<FileInfoDao, FileInfo> implements FileInfoService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    protected abstract FileInfoDao getFileDao();

    @Override
    public FileInfo upload(MultipartFile file, String filePath, String bizType) throws Exception {
        FileInfo fileInfo = this.getFileInfo(file);
        FileInfo oldFileInfo = getFileInfoByMd5(fileInfo);
        if (oldFileInfo != null) {
            logger.info("{}已经存在了,不需要在次上传!", file.getOriginalFilename());
            return oldFileInfo;
        }
        upload(file.getInputStream(), filePath, bizType, fileInfo);
        return fileInfo;
    }

    protected FileInfo getFileInfo(MultipartFile file) throws Exception {
        String md5 = FileUtils.fileMd5(file.getInputStream());
        FileInfo fileInfo = new FileInfo();
        fileInfo.setMd5(md5);// 将文件的md5设置为文件表的id
        fileInfo.setName(file.getOriginalFilename());
        fileInfo.setContentType(file.getContentType());
        fileInfo.setSize(file.getSize());
        fileInfo.setFileSuffix(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        fileInfo.setId(DefaultIdentifierGeneratorUtils.getGeneratorLongId());
        return fileInfo;
    }

    private void upload(InputStream inputStream, String filePath, String bizType, FileInfo fileInfo) throws Exception {
//        if (!fileInfo.getName().contains(".")) {
//            throw new IllegalArgumentException("缺少后缀名");
//        }
        String fileName = fileInfo.getId() + fileInfo.getFileSuffix();
        uploadFile(FileUtils.getFilePhysicalPath(filePath), fileName, inputStream);
        fileInfo.setPath(FileUtils.getFileFullPath(filePath) + fileName);
        fileInfo.setPhysicalPath(FileUtils.getFilePhysicalPath(filePath) + fileName);
        fileInfo.setBizType(bizType);
        getFileDao().insert(fileInfo);// 将文件信息保存到数据库
        logger.info("上传文件：{}", fileInfo);
    }

    private FileInfo getFileInfoByMd5(FileInfo fileInfo) {
        LambdaQueryWrapper<FileInfo> query = new LambdaQueryWrapper<>();
        query.eq(FileInfo::getMd5, fileInfo.getMd5());
        return getFileDao().selectOne(query);
    }

    /**
     * 上传文件
     *
     * @param file
     * @param fileInfo
     */
    protected abstract void uploadFile(MultipartFile file, FileInfo fileInfo) throws Exception;

    protected abstract String uploadFileSub(String pathName, String fileName, InputStream inputStream, boolean chunkOne) throws Exception;

    protected abstract void uploadFile(String pathName, String fileName, String originfilename) throws Exception;

    protected abstract String uploadFile(String pathName, String fileName, InputStream inputStream) throws Exception;

    protected abstract void downloadFile(String pathName, String filename, String localpath) throws Exception;


    @Override
    public void delete(FileInfo fileInfo) {
        deleteFile(fileInfo);
        getFileDao().deleteById(fileInfo.getId());
        logger.info("删除文件：{}", fileInfo);
    }

    /**
     * 删除文件资源
     *
     * @param fileInfo
     * @return
     */
    protected abstract boolean deleteFile(FileInfo fileInfo);

    @Override
    public FileInfo getById(String id) {
        return getFileDao().selectById(id);
    }

    @Override
    public PageResult<FileInfo> findFileInfoByPaged(PageRequest<FileInfo> t) {
        MybatisPageServiceTemplate<FileInfo, FileInfo> query = new MybatisPageServiceTemplate<FileInfo, FileInfo>(this.baseMapper) {
            @Override
            protected AbstractWrapper constructWrapper(FileInfo fileInfo) {
                LambdaQueryWrapper<FileInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                if (fileInfo != null) {
                    if (StringUtils.isNotBlank(fileInfo.getName())) {
                        lambdaQueryWrapper.like(FileInfo::getName, fileInfo.getName());
                    }
                    if (StringUtils.isNotBlank(fileInfo.getName())) {
                        lambdaQueryWrapper.like(FileInfo::getName, fileInfo.getName());
                    }
                }
                return lambdaQueryWrapper;
            }
        };
        return query.doPagingQuery(t);
    }
}
