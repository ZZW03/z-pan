package com.zzw.zpan.modules.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzw.zpan.modules.file.context.FileChunkMergeAndSaveContext;
import com.zzw.zpan.modules.file.context.FileSaveContext;
import com.zzw.zpan.modules.file.context.QueryFileListContext;
import com.zzw.zpan.modules.file.context.QueryRealFileListContext;
import com.zzw.zpan.modules.file.entity.RPanFile;


import java.util.List;


public interface IFileService extends IService<RPanFile> {

    List<RPanFile> getFileList(QueryRealFileListContext context);

    /**
     * 上传单文件并保存实体记录
     *
     * @param context
     */
    void saveFile(FileSaveContext context);

    /**
     * 合并物理文件并保存物理文件记录
     *
     * @param context
     */
    void mergeFileChunkAndSaveFile(FileChunkMergeAndSaveContext context);


}
