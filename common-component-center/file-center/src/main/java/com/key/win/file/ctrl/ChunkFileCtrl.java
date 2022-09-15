package com.key.win.file.ctrl;

import com.key.win.basic.exception.BizException;
import com.key.win.basic.util.FileUtils;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.basic.web.Result;
import com.key.win.file.config.ChunkFileServiceFactory;
import com.key.win.file.config.FileServiceFactory;
import com.key.win.file.model.ChunkFile;
import com.key.win.file.model.FileInfo;
import com.key.win.file.service.ChunkFileService;
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
public class ChunkFileCtrl {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String AUTHORITY_PREFIX = "common::file::FileInfo::";


    @Autowired
    private ChunkFileServiceFactory chunkFileServiceFactory;


    @PostMapping("/file/getFileInfoByPaged")
    @ApiOperation(value = "File分页")
    @LogAnnotation(module = "file-center", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::PAGED')")
    public PageResult<ChunkFile> findFileInfoByPaged(@RequestBody PageRequest<ChunkFile> pageRequest) {
        ChunkFileService fileService = chunkFileServiceFactory.getFileService();
        return fileService.findFileInfoByPaged(pageRequest);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "File分页")
    @LogAnnotation(module = "file-center", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::ID')")
    public Result<ChunkFile> get(@PathVariable String id) {
        ChunkFileService fileService = chunkFileServiceFactory.getFileService();
        return Result.succeed(fileService.getById(id));
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
            ChunkFile fileInfo = chunkFileServiceFactory.getFileService().getById(id);
            if (fileInfo != null) {
                chunkFileServiceFactory.getFileService().delete(fileInfo);
            }
            return Result.succeed("操作成功");
        } catch (Exception ex) {
            return Result.failed("操作失败");
        }
    }


    @PostMapping("/chunk")
    @LogAnnotation(module = "file-center", recordRequestParam = false)
    @ApiOperation(value = "分片上传")
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "UPLOAD')")
    public Result uploadChunk(@RequestBody ChunkFile chunkFile) throws Exception {
        chunkFileServiceFactory.getFileService().upload(chunkFile);
//        MultipartFile file = chunkFile.getFile();
//        logger.debug("file originName: {}, chunkNumber: {}", file.getOriginalFilename(), chunkFile.getChunkNumber());
//
//        FileUtils.uploadFile(file.getInputStream(), FileUtils.getChunkFilePhysicalPath(path, chunkFile.getMd5()), chunkFile.getFilename() + "-" + chunkFile.getChunkNumber());
//        logger.debug("文件 {} 写入成功, uuid:{}", chunkFile.getFilename(), chunkFile.getMd5());
//        chunkService.saveChunk(chunkFile);
        return Result.succeed("操作成功");
    }

    @GetMapping("/chunk/{md5}/{chunkNumber}")
    @LogAnnotation(module = "file-center", recordRequestParam = false)
    @ApiOperation(value = "分片上传")
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "UPLOAD')")
    public Result checkChunk(@PathVariable String md5, @PathVariable Integer chunkNumber) {
        if (chunkFileServiceFactory.getFileService().checkChunk(md5, chunkNumber)) {
            return Result.succeed(true);
        }
        return Result.succeed(false);
    }

    @PostMapping("/mergeFile")
    @LogAnnotation(module = "file-center", recordRequestParam = false)
    @ApiOperation(value = "分片上传")
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "UPLOAD')")
    public String mergeFile(@RequestBody FileInfo fileInfo) {

        // FileUtils.merge(file, folder, filename);

        return "合并成功";
    }
}
