package com.zzw.zpan.modules.file.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zzw.zpan.modules.file.context.FileChunkSaveContext;
import com.zzw.zpan.modules.file.entity.RPanFileChunk;

/**
 * @author imooc
 * @description 针对表【r_pan_file_chunk(文件分片信息表)】的数据库操作Service
 * @createDate 2022-11-09 18:36:41
 */
public interface IFileChunkService extends IService<RPanFileChunk> {

    /**
     * 文件分片保存
     *
     * @param context
     */
    void saveChunkFile(FileChunkSaveContext context);

}
