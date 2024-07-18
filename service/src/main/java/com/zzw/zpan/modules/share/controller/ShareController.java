package com.zzw.zpan.modules.share.controller;


import com.google.common.base.Splitter;
import com.zzw.zpan.common.annotation.needCode;
import com.zzw.zpan.common.utils.ShareIdUtil;
import com.zzw.zpan.common.utils.UserIdUtil;
import com.zzw.zpan.constants.RPanConstants;
import com.zzw.zpan.modules.file.vo.RPanUserFileVO;
import com.zzw.zpan.modules.share.context.*;
import com.zzw.zpan.modules.share.po.CancelSharePO;
import com.zzw.zpan.modules.share.po.CheckShareCodePO;
import com.zzw.zpan.modules.share.po.CreateShareUrlPO;
import com.zzw.zpan.modules.share.po.ShareSavePO;
import com.zzw.zpan.modules.share.service.iShareService;
import com.zzw.zpan.modules.share.vo.RPanShareUrlListVO;
import com.zzw.zpan.modules.share.vo.RPanShareUrlVO;
import com.zzw.zpan.modules.share.vo.ShareDetailVO;
import com.zzw.zpan.modules.share.vo.ShareSimpleDetailVO;
import com.zzw.zpan.response.R;
import com.zzw.zpan.utils.IdUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("share")
public class ShareController {

    @Resource
    iShareService iShareService;


    //创建分享模块
    @PostMapping
    public R<RPanShareUrlVO> create(@Validated @RequestBody CreateShareUrlPO createShareUrlPO){
        CreateShareUrlContext context = createShareUrlPO.asViewObject(CreateShareUrlContext.class, o -> {
            o.setUserId(UserIdUtil.get());
            o.setShareType(createShareUrlPO.getShareType());
            o.setShareName(createShareUrlPO.getShareName());
            o.setShareDayType(createShareUrlPO.getShareDayType());
        });

        String shareFileIds = createShareUrlPO.getShareFileIds();
        List<Long> shareFileIdList =
                Splitter.on(RPanConstants.COMMON_SEPARATOR).splitToList(shareFileIds).stream().map(IdUtil::decrypt).toList();

        context.setShareFileIdList(shareFileIdList);
        RPanShareUrlVO vo = iShareService.create(context);
        return R.data(vo);


    }



    //value = "查询分享链接列表",
    @GetMapping("shares")
    public R<List<RPanShareUrlListVO>> getShares() {
        QueryShareListContext context = new QueryShareListContext();
        context.setUserId(UserIdUtil.get());
        List<RPanShareUrlListVO> result = iShareService.getShares(context);
        return R.data(result);
    }


    //value = "取消分享",
    @DeleteMapping
    public R cancelShare(@Validated @RequestBody CancelSharePO cancelSharePO) {
        CancelShareContext context = new CancelShareContext();

        context.setUserId(UserIdUtil.get());

        String shareIds = cancelSharePO.getShareIds();
        List<Long> shareIdList = Splitter.on(RPanConstants.COMMON_SEPARATOR).splitToList(shareIds).stream().map(IdUtil::decrypt).collect(Collectors.toList());
        context.setShareIdList(shareIdList);

        iShareService.cancelShare(context);
        return R.success();
    }



    //value = "校验分享码",
    @PostMapping("code/check")
    public R<String> checkShareCode(@Validated @RequestBody CheckShareCodePO checkShareCodePO) {
        CheckShareCodeContext context = new CheckShareCodeContext();

        context.setShareId(IdUtil.decrypt(checkShareCodePO.getShareId()));
        context.setShareCode(checkShareCodePO.getShareCode());

        String token = iShareService.checkShareCode(context);
        return R.data(token);
    }



    //value = "查询分享的详情",
    @needCode
    @GetMapping
    public R<ShareDetailVO> detail() {
        QueryShareDetailContext context = new QueryShareDetailContext();
        context.setShareId(ShareIdUtil.get());
        ShareDetailVO vo = iShareService.detail(context);
        return R.data(vo);
    }

    //value = "查询分享的简单详情",
    @GetMapping("simple")
    public R<ShareSimpleDetailVO> simpleDetail(@NotBlank(message = "分享的ID不能为空") @RequestParam(value = "shareId", required = false) String shareId) {
        QueryShareSimpleDetailContext context = new QueryShareSimpleDetailContext();
        context.setShareId(IdUtil.decrypt(shareId));
        ShareSimpleDetailVO vo = iShareService.simpleDetail(context);
        return R.data(vo);
    }



    // = "获取下一级文件列表",
    @GetMapping("share/file/list")
    @needCode
    public R<List<RPanUserFileVO>> fileList(@NotBlank(message = "文件的父ID不能为空") @RequestParam(value = "parentId", required = false) String parentId) {
        QueryChildFileListContext context = new QueryChildFileListContext();
        context.setShareId(ShareIdUtil.get());
        context.setParentId(IdUtil.decrypt(parentId));
        List<RPanUserFileVO> result = iShareService.fileList(context);
        return R.data(result);
    }


    //value = "保存至我的网盘",
    @needCode
    @PostMapping("save")
    public R saveFiles(@Validated @RequestBody ShareSavePO shareSavePO) {
        ShareSaveContext context = new ShareSaveContext();

        String fileIds = shareSavePO.getFileIds();
        List<Long> fileIdList = Splitter.on(RPanConstants.COMMON_SEPARATOR).splitToList(fileIds).stream().map(IdUtil::decrypt).collect(Collectors.toList());
        context.setFileIdList(fileIdList);

        context.setTargetParentId(IdUtil.decrypt(shareSavePO.getTargetParentId()));
        context.setUserId(UserIdUtil.get());
        context.setShareId(ShareIdUtil.get());

        iShareService.saveFiles(context);
        return R.success();
    }

    //value = "分享文件下载",
    @GetMapping("share/file/download")
    @needCode
    public void download(@NotBlank(message = "文件ID不能为空") @RequestParam(value = "fileId", required = false) String fileId,
                         HttpServletResponse response) {
        ShareFileDownloadContext context = new ShareFileDownloadContext();
        context.setFileId(IdUtil.decrypt(fileId));
        context.setShareId(ShareIdUtil.get());
        context.setUserId(UserIdUtil.get());
        context.setResponse(response);
        iShareService.download(context);
    }


}
