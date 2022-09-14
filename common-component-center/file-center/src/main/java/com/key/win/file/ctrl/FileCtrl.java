package com.key.win.file.ctrl;

import com.key.win.basic.exception.BizException;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.basic.web.Result;
import com.key.win.file.config.FileServiceFactory;
import com.key.win.file.model.FileInfo;
import com.key.win.file.service.FileInfoService;
import com.key.win.file.util.Base64Util;
import com.key.win.file.util.FilePropertyUtils;
import com.key.win.file.vo.FileBase64Vo;
import com.key.win.log.annotation.LogAnnotation;
import com.key.win.security.annotation.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api("File相关的api")
public class FileCtrl {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String AUTHORITY_PREFIX = "common::file::FileInfo::";

    @Autowired
    private FileServiceFactory fileServiceFactory;


    @PostMapping("/file/getFileInfoByPaged")
    @ApiOperation(value = "File分页")
    @LogAnnotation(module = "file-center", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::PAGED')")
    public PageResult<FileInfo> findFileInfoByPaged(@RequestBody PageRequest<FileInfo> pageRequest) {
        FileInfoService fileService = fileServiceFactory.getFileService();
        return fileService.findFileInfoByPaged(pageRequest);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "File分页")
    @LogAnnotation(module = "file-center", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::ID')")
    public Result<FileInfo> get(@PathVariable String id) {
        FileInfoService fileService = fileServiceFactory.getFileService();
        return Result.succeed(fileService.getById(id));
    }

    /**
     * 文件上传
     *
     * @param file
     * @return
     * @throws Exception
     */
    @LogAnnotation(module = "file-center", recordRequestParam = false)
    @PostMapping("/upload/{bizType}/file")
    @ApiOperation(value = "上传附件")
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "UPLOAD')")
    public Result<FileInfo> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String bizType) throws Exception {
        if (file.getOriginalFilename().lastIndexOf(".") == -1) {
            throw new BizException("originalFilename不在存在文件后缀！");
        }
        String path = bizTypeCheck(bizType);
        return uploadFile(bizType, path, file);
    }

    /**
     * 文件上传
     *
     * @param files
     * @return
     * @throws Exception
     */
    @LogAnnotation(module = "file-center", recordRequestParam = false)
    @PostMapping("/upload/{bizType}/files")
    @ApiOperation(value = "上传附件")
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "UPLOAD')")
    public Result<FileInfo> uploadFiles(@RequestParam("files") MultipartFile[] files, @PathVariable String bizType) throws Exception {
        for (MultipartFile file : files) {
            if (file.getOriginalFilename().lastIndexOf(".") == -1) {
                throw new BizException("originalFilename不在存在文件后缀！");
            }
            String path = bizTypeCheck(bizType);
            uploadFile(bizType, path, file);
        }
        return Result.succeed();
    }

    /**
     * 文件上传
     * base64编码
     *
     * @return
     * @throws Exception
     */
    @LogAnnotation(module = "file-center", recordRequestParam = false)
    @PostMapping("/upload/{bizType}/base64")
    @ApiOperation(value = "上传附件")
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "UPLOAD')")
    public Result<FileInfo> uploadBase64File(@RequestBody FileBase64Vo fileBase64Vo, @PathVariable String bizType) throws Exception {
        String path = bizTypeCheck(bizType);
        MultipartFile multipartFile = Base64Util.base64ToMultipart(fileBase64Vo.getQrcode(), fileBase64Vo.getName());
        return uploadFile(bizType, path, multipartFile);
    }

    private Result<FileInfo> uploadFile(@PathVariable String bizType, String path, MultipartFile multipartFile) throws Exception {
        FileInfoService fileService = fileServiceFactory.getFileService();
        return Result.succeed(fileService.upload(multipartFile, path, bizType));
    }

    private String bizTypeCheck(@PathVariable String bizType) {
        if (StringUtils.isBlank(bizType)) {
            logger.error("上传文件业务类型为空！");
            throw new BizException("上传文件业务类型为空！");
        }
        String path = FilePropertyUtils.getPropertyByUploadBiz(bizType);
        if (StringUtils.isBlank(path)) {
            logger.error("上传文件业务路径不存在！");
            throw new BizException("上传文件业务路径不存在！");
        }
        return path;
    }


    /**
     * 文件删除
     *
     * @param id
     */
    @LogAnnotation(module = "file-center", recordRequestParam = false)
    @DeleteMapping("/files/{id}")
    @ApiOperation(value = "删除附件")
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "DELETE')")
    public Result delete(@PathVariable String id) {

        try {
            FileInfo fileInfo = fileServiceFactory.getFileService().getById(id);
            if (fileInfo != null) {
                fileServiceFactory.getFileService().delete(fileInfo);
            }
            return Result.succeed("操作成功");
        } catch (Exception ex) {
            return Result.failed("操作失败");
        }

    }
}
