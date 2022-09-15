package com.key.win.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.file.model.ChunkFile;
import com.key.win.file.model.FileInfo;


public interface ChunkFileService extends IService<ChunkFile> {

    ChunkFile upload(ChunkFile chunkFile) throws Exception;

    /**
     * 保存文件块
     *
     * @param chunk
     */
    boolean saveChunk(ChunkFile chunk);

    /**
     * 检查文件块是否存在
     *
     * @param md5
     * @param chunkNumber
     * @return
     */
    boolean checkChunk(String md5, Integer chunkNumber);

    boolean merge(FileInfo fileInfo);


    void delete(ChunkFile chunkFile);

    ChunkFile getById(String id);

    PageResult<ChunkFile> findFileInfoByPaged(PageRequest<ChunkFile> t);
}
