package com.zzw.zpan.modules.file.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzw.zpan.OSSStorageEngine;
import com.zzw.zpan.common.config.PanServerConfig;
import com.zzw.zpan.context.StoreFileChunkContext;
import com.zzw.zpan.exception.RPanBusinessException;
import com.zzw.zpan.modules.file.context.FileChunkSaveContext;
import com.zzw.zpan.modules.file.entity.RPanFileChunk;
import com.zzw.zpan.modules.file.enums.enums.MergeFlagEnum;
import com.zzw.zpan.modules.file.mapper.RPanFileChunkMapper;
import com.zzw.zpan.modules.file.service.IFileChunkService;
import com.zzw.zpan.utils.IdUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * @author imooc
 * @description 针对表【r_pan_file_chunk(文件分片信息表)】的数据库操作Service实现
 * @createDate 2022-11-09 18:36:41
 */
@Service
public class FileChunkServiceImpl extends ServiceImpl<RPanFileChunkMapper, RPanFileChunk> implements IFileChunkService {

    @Resource
    private PanServerConfig config;


    @Resource
    private OSSStorageEngine storageEngine;

    /**
     * 文件分片保存
     * <p>
     * 1、保存文件分片和记录
     * 2、判断文件分片是否全部上传完成
     *
     * @param context
     */
    @Override
    public void saveChunkFile(FileChunkSaveContext context) {
        doSaveChunkFile(context);
        doJudgeMergeFile(context);
    }

    /**
     * 判断是否所有的分片均没上传完成
     *
     * @param context
     */
    private void doJudgeMergeFile(FileChunkSaveContext context) {
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.eq("identifier", context.getIdentifier());
        queryWrapper.eq("create_user", context.getUserId());
        int count = (int) count(queryWrapper);
        if (count == context.getTotalChunks().intValue()) {
            context.setMergeFlagEnum(MergeFlagEnum.READY);
        }
    }

    /**
     * 执行文件分片上传保存的操作
     * <p>
     * 1、委托文件存储引擎存储文件分片
     * 2、保存文件分片记录
     *
     * @param context
     */
    private void doSaveChunkFile(FileChunkSaveContext context) {
        doStoreFileChunk(context);
        doSaveRecord(context);
    }

    /**
     * 保存文件分片记录
     *
     * @param context
     */
    private void doSaveRecord(FileChunkSaveContext context) {
        RPanFileChunk record = new RPanFileChunk();
        record.setId(IdUtil.get());
        record.setIdentifier(context.getIdentifier());
        record.setRealPath(context.getRealPath());
        record.setChunkNumber(context.getChunkNumber());
        record.setExpirationTime(DateUtil.offsetDay(new Date(), config.getChunkFileExpirationDays()));
        record.setCreateUser(context.getUserId());
        record.setCreateTime(new Date());
        if (!save(record)) {
            throw new RPanBusinessException("文件分片上传失败");
        }
    }

    /**
     * 委托文件存储引擎保存文件分片
     *
     * @param context
     */
    private void doStoreFileChunk(FileChunkSaveContext context) {
        try {
            StoreFileChunkContext storeFileChunkContext = context.asViewObject(StoreFileChunkContext.class, o -> {
                o.setChunkNumber(context.getChunkNumber());
                o.setFilename(context.getFilename());
                o.setTotalChunks(context.getTotalChunks());
                o.setRealPath(context.getRealPath());
                o.setIdentifier(context.getIdentifier());
                o.setFilename(context.getFilename());
                o.setUserId(context.getUserId());
                o.setCurrentChunkSize(context.getCurrentChunkSize());
            });
            storeFileChunkContext.setInputStream(context.getFile().getInputStream());
            storageEngine.storeChunk(storeFileChunkContext);
            context.setRealPath(storeFileChunkContext.getRealPath());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RPanBusinessException("文件分片上传失败");
        }
    }

}




