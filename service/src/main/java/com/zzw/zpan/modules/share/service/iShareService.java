package com.zzw.zpan.modules.share.service;

import com.zzw.zpan.modules.file.entity.RPanUserFile;
import com.zzw.zpan.modules.file.vo.RPanUserFileVO;
import com.zzw.zpan.modules.share.context.*;
import com.zzw.zpan.modules.share.vo.RPanShareUrlListVO;
import com.zzw.zpan.modules.share.vo.RPanShareUrlVO;
import com.zzw.zpan.modules.share.vo.ShareDetailVO;
import com.zzw.zpan.modules.share.vo.ShareSimpleDetailVO;

import java.util.List;

public interface iShareService {

    /**
     * 创建分享链接
     *
     * @param context
     * @return RPanShareUrlVO
     */
    RPanShareUrlVO create(CreateShareUrlContext context);


    /**
     * 查询用户的分享列表
     *
     * @param context
     * @return
     */
    List<RPanShareUrlListVO> getShares(QueryShareListContext context);


    /**
     * 取消分享链接
     *
     * @param context
     */
    void cancelShare(CancelShareContext context);


    /**
     * 校验分享码
     *
     * @param context
     * @return
     */
    String checkShareCode(CheckShareCodeContext context);

    /**
     * 查询分享的详情
     *
     * @param context
     * @return
     */
    ShareDetailVO detail(QueryShareDetailContext context);


    /**
     * 查询分享的简单详情
     *
     * @param context
     * @return
     */
    ShareSimpleDetailVO simpleDetail(QueryShareSimpleDetailContext context);

    /**
     * 获取下一级的文件列表
     *
     * @param context
     * @return
     */
    List<RPanUserFileVO> fileList(QueryChildFileListContext context);


    /**
     * 转存至我的网盘
     *
     * @param context
     */
    void saveFiles(ShareSaveContext context);

    /**
     * 分享的文件下载
     *
     * @param context
     */
    void download(ShareFileDownloadContext context);




}
