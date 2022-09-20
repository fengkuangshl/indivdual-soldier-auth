package com.key.win.file.ctrl;

import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.basic.web.Result;
import com.key.win.file.config.ChunkFileServiceFactory;
import com.key.win.file.config.FileServiceFactory;
import com.key.win.file.model.ChunkFile;
import com.key.win.file.model.FileInfo;
import com.key.win.file.service.ChunkFileService;
import com.key.win.file.vo.ChunkFileResponseVo;
import com.key.win.log.annotation.LogAnnotation;
import com.key.win.security.annotation.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Api("File相关的api")
public class ChunkFileCtrl {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String AUTHORITY_PREFIX = "common::file::FileInfo::";


    @Autowired
    private ChunkFileServiceFactory chunkFileServiceFactory;

    @Autowired
    private FileServiceFactory fileServiceFactory;


    @PostMapping("/chunk/file/getFileInfoByPaged")
    @ApiOperation(value = "File分页")
    @LogAnnotation(module = "file-center", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::PAGED')")
    public PageResult<ChunkFile> findFileInfoByPaged(@RequestBody PageRequest<ChunkFile> pageRequest) {
        ChunkFileService fileService = chunkFileServiceFactory.getFileService();
        return fileService.findFileInfoByPaged(pageRequest);
    }

    @GetMapping("/chunk/get/{id}")
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
    @DeleteMapping("/chunk/files/{id}")
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
    public Result uploadChunk(ChunkFile chunkFile) throws Exception {
        ChunkFileResponseVo chunkFileCheckResponseVo = new ChunkFileResponseVo();
        chunkFileServiceFactory.getFileService().upload(chunkFile);
        FileInfo fileInfoByMd5 = fileServiceFactory.getFileService().getFileInfoByMd5(chunkFile.getIdentifier());
        if (fileInfoByMd5 != null) {
            chunkFileCheckResponseVo.setSkipUpload(false);
            chunkFileCheckResponseVo.setAccessPath(fileInfoByMd5.getAccessPath());
            chunkFileCheckResponseVo.setContentType(fileInfoByMd5.getContentType());
            chunkFileCheckResponseVo.setTitle(fileInfoByMd5.getName());
            chunkFileCheckResponseVo.setResourceId(fileInfoByMd5.getId());
            return Result.succeed(chunkFileCheckResponseVo);
        }
        return Result.failed("上传失败");
    }

    @GetMapping("/chunk")
    @LogAnnotation(module = "file-center", recordRequestParam = false)
    @ApiOperation(value = "分片上传")
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "UPLOAD')")
    public Result checkChunk(@RequestParam Map<String, Object> chunkFile) {
        ChunkFileResponseVo chunkFileCheckResponseVo = new ChunkFileResponseVo();
        String identifier = chunkFile.get("identifier").toString();
        FileInfo fileInfoByMd5 = fileServiceFactory.getFileService().getFileInfoByMd5(identifier);
        if (fileInfoByMd5 != null) {
            chunkFileCheckResponseVo.setSkipUpload(true);
            chunkFileCheckResponseVo.setAccessPath(fileInfoByMd5.getAccessPath());
            chunkFileCheckResponseVo.setContentType(fileInfoByMd5.getContentType());
            chunkFileCheckResponseVo.setTitle(fileInfoByMd5.getName());
            chunkFileCheckResponseVo.setResourceId(fileInfoByMd5.getId());
            return Result.succeed(chunkFileCheckResponseVo);
        }
        ChunkFile cf = new ChunkFile();
        cf.setIdentifier(identifier);
        List<ChunkFile> chunkFiles = chunkFileServiceFactory.getFileService().findChunkFile(cf);
        if (!CollectionUtils.isEmpty(chunkFiles)) {
            List<Long> collect = chunkFiles.stream().map(ChunkFile::getChunkNumber).collect(Collectors.toList());
            Collections.sort(collect);
            chunkFileCheckResponseVo.setUploaded(collect);
        }
        return Result.succeed(chunkFileCheckResponseVo);
    }

    @PostMapping("/chunk/mergeFile")
    @LogAnnotation(module = "file-center", recordRequestParam = false)
    @ApiOperation(value = "分片上传")
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "UPLOAD')")
    public Result mergeFile(@RequestBody ChunkFile fileInfo) throws Exception {
        // FileUtils.merge(file, folder, filename);
        boolean merge = chunkFileServiceFactory.getFileService().merge(fileInfo);
        return Result.succeed(merge ? "合并成功" : "合并失败");
    }
}
