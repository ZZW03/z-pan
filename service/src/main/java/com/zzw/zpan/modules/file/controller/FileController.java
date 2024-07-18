package com.zzw.zpan.modules.file.controller;


import com.google.common.base.Splitter;
import com.zzw.zpan.common.utils.UserIdUtil;
import com.zzw.zpan.constants.RPanConstants;
import com.zzw.zpan.modules.file.constants.FileConstants;
import com.zzw.zpan.modules.file.context.*;
import com.zzw.zpan.modules.file.enums.enums.DelFlagEnum;
import com.zzw.zpan.modules.file.po.*;
import com.zzw.zpan.modules.file.service.IUserFileService;
import com.zzw.zpan.modules.file.vo.*;
import com.zzw.zpan.response.R;
import com.zzw.zpan.utils.IdUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api")
public class FileController {

    @Resource
    private IUserFileService iUserFileService;

    //查询文件列表
    //parentId 是主文件的id 查询的是主文件下的问价列表
    @GetMapping("/files")
    public R<List<RPanUserFileVO>> list(@NotBlank(message = "父文件夹ID不能为空") @RequestParam(value = "parentId", required = false) String parentId,
                                        @RequestParam(value = "fileTypes", required = false, defaultValue = FileConstants.ALL_FILE_TYPE) String fileTypes) {
        Long realParentId = -1L;
        if (!FileConstants.ALL_FILE_TYPE.equals(parentId)) {
            realParentId = IdUtil.decrypt(parentId);
        }

        List<Integer> fileTypeArray = null;

        if (!Objects.equals(FileConstants.ALL_FILE_TYPE, fileTypes)) {
            fileTypeArray = Splitter.on(RPanConstants.COMMON_SEPARATOR).splitToList(fileTypes).stream().map(Integer::valueOf).collect(Collectors.toList());
        }

        QueryFileListContext context = new QueryFileListContext();
        context.setParentId(realParentId);
        context.setFileTypeArray(fileTypeArray);
        context.setUserId(UserIdUtil.get());
        context.setDelFlag(DelFlagEnum.NO.getCode());


        List<RPanUserFileVO> result = iUserFileService.getFileList(context);
        return R.data(result);
    }


    //创建文件夹
    @PostMapping("file/folder")
    public R<String> createFolder(@Validated @RequestBody CreateFolderPO createFolderPO) {
        System.out.println(123);
        CreateFolderContext context = createFolderPO.asViewObject(CreateFolderContext.class,o->{
            o.setFolderName(createFolderPO.getFolderName());
            o.setParentId((createFolderPO.getParentId()));
            o.setUserId(UserIdUtil.get());
        });

        Long fileId = iUserFileService.createFolder(context);
        return R.data(IdUtil.encrypt(fileId));
    }

    //文件重命名
    @PutMapping("file")
    public R updateFilename(@Validated @RequestBody UpdateFilenamePO updateFilenamePO) {
        UpdateFilenameContext context = updateFilenamePO.asViewObject(UpdateFilenameContext.class,o->{
            o.setFileId(updateFilenamePO.getFileId());
            o.setNewFilename(updateFilenamePO.getNewFilename());
            o.setUserId(UserIdUtil.get());
        });
        iUserFileService.updateFilename(context);
        return R.success();
    }

    //批量删除文件
    @DeleteMapping("file")
    public R deleteFile(@Validated @RequestBody DeleteFilePO deleteFilePO) {
        DeleteFileContext context = new DeleteFileContext();

        String fileIds = deleteFilePO.getFileIds();


        List<Long> collect = Splitter.on(RPanConstants.COMMON_SEPARATOR).splitToList(fileIds).stream().map(Long::valueOf).collect(Collectors.toList());

        context.setFileIdList(collect);
        context.setUserId(UserIdUtil.get());
        iUserFileService.deleteFile(context);
        return R.success();
    }


    //value = "文件秒传",
    @PostMapping("file/sec-upload")
    public R secUpload(@Validated @RequestBody SecUploadFilePO secUploadFilePO) {

        SecUploadFileContext context = secUploadFilePO.asViewObject(SecUploadFileContext.class,o->{
            o.setFilename(secUploadFilePO.getFilename());
            o.setUserId(UserIdUtil.get());
            o.setParentId((secUploadFilePO.getParentId()));
            o.setIdentifier(secUploadFilePO.getIdentifier());
        });

        boolean result = iUserFileService.secUpload(context);

        if (result) {
            return R.success();
        }
        return R.fail("文件唯一标识不存在，请手动执行文件上传");
    }


    //value = "单文件上传",
    @PostMapping("file/upload")
    public R upload(@Validated FileUploadPO fileUploadPO) {

        long size = fileUploadPO.getFile().getSize();

        FileUploadContext context = fileUploadPO.asViewObject(FileUploadContext.class,o->{
            o.setFile(fileUploadPO.getFile());
            o.setTotalSize(size);
            o.setUserId(UserIdUtil.get());
            o.setIdentifier(fileUploadPO.getIdentifier());
            o.setParentId((fileUploadPO.getParentId()));
            o.setFilename(fileUploadPO.getFilename());
        });

        iUserFileService.upload(context);
        return R.success();
    }


    // = "文件分片上传",
    @PostMapping("file/chunk-upload")
    public R<FileChunkUploadVO> chunkUpload(@Validated FileChunkUploadPO fileChunkUploadPO) {
        FileChunkUploadContext context = fileChunkUploadPO.asViewObject(FileChunkUploadContext.class,o->{
            o.setFile(fileChunkUploadPO.getFile());
            o.setFilename(fileChunkUploadPO.getFilename());
            o.setIdentifier(fileChunkUploadPO.getIdentifier());
            o.setChunkNumber(fileChunkUploadPO.getChunkNumber());
            o.setCurrentChunkSize(fileChunkUploadPO.getCurrentChunkSize());
            o.setTotalSize(fileChunkUploadPO.getTotalSize());
            o.setUserId(UserIdUtil.get());
            o.setTotalChunks(fileChunkUploadPO.getTotalChunks());
        });
        FileChunkUploadVO vo = iUserFileService.chunkUpload(context);
        return R.data(vo);
    }


    //value = "查询已经上传的文件分片列表",
    @GetMapping("file/chunk-upload")
    public R<UploadedChunksVO> getUploadedChunks(@Validated QueryUploadedChunksPO queryUploadedChunksPO) {
        QueryUploadedChunksContext context = queryUploadedChunksPO.asViewObject(QueryUploadedChunksContext.class, o -> {
            o.setIdentifier(queryUploadedChunksPO.getIdentifier());
            o.setUserId(UserIdUtil.get());
        });
        UploadedChunksVO vo = iUserFileService.getUploadedChunks(context);
        return R.data(vo);
    }


    //value = "文件分片合并",
    @PostMapping("file/merge")
    public R mergeFile(@Validated @RequestBody FileChunkMergePO fileChunkMergePO) {
        FileChunkMergeContext context = fileChunkMergePO.asViewObject(FileChunkMergeContext.class, o -> {
            o.setIdentifier(fileChunkMergePO.getIdentifier());
            o.setUserId(UserIdUtil.get());
            o.setParentId(fileChunkMergePO.getParentId());
            o.setTotalSize(fileChunkMergePO.getTotalSize());
            o.setFilename(fileChunkMergePO.getFilename());
        });

        iUserFileService.mergeFile(context);
        return R.success();
    }

    // value = "文件预览",
    @GetMapping("file/preview")
    public void preview(@NotBlank(message = "文件ID不能为空") @RequestParam(value = "fileId", required = false) String fileId,
                        HttpServletResponse response) {
        FilePreviewContext context = new FilePreviewContext();
        context.setFileId(IdUtil.decrypt(fileId));
        context.setResponse(response);
        context.setUserId(UserIdUtil.get());
        iUserFileService.preview(context);

    }


    // value = "查询文件夹树",
    @GetMapping("file/folder/tree")
    public R<List<FolderTreeNodeVO>> getFolderTree() {
        QueryFolderTreeContext context = new QueryFolderTreeContext();
        context.setUserId(UserIdUtil.get());
        List<FolderTreeNodeVO> result = iUserFileService.getFolderTree(context);
        return R.data(result);
    }


    //value = "文件转移",
    @PostMapping("file/transfer")
    public R transfer(@Validated @RequestBody TransferFilePO transferFilePO) {
        String fileIds = transferFilePO.getFileIds();
        String targetParentId = transferFilePO.getTargetParentId();
        List<Long> fileIdList = Splitter.on(RPanConstants.COMMON_SEPARATOR).splitToList(fileIds).stream().map(IdUtil::decrypt).collect(Collectors.toList());
        TransferFileContext context = new TransferFileContext();
        context.setFileIdList(fileIdList);
        context.setTargetParentId(IdUtil.decrypt(targetParentId));
        context.setUserId(UserIdUtil.get());
        iUserFileService.transfer(context);
        return R.success();
    }



    // = "文件复制",
    @PostMapping("file/copy")
    public R copy(@Validated @RequestBody CopyFilePO copyFilePO) {
        String fileIds = copyFilePO.getFileIds();
        String targetParentId = copyFilePO.getTargetParentId();
        List<Long> fileIdList = Splitter.on(RPanConstants.COMMON_SEPARATOR).splitToList(fileIds).stream().map(IdUtil::decrypt).collect(Collectors.toList());
        CopyFileContext context = new CopyFileContext();
        context.setFileIdList(fileIdList);
        context.setTargetParentId(IdUtil.decrypt(targetParentId));
        context.setUserId(UserIdUtil.get());
        iUserFileService.copy(context);
        return R.success();
    }


    //value = "文件搜索",
    @GetMapping("file/search")
    public R<List<FileSearchResultVO>> search(@Validated FileSearchPO fileSearchPO) {
        FileSearchContext context = new FileSearchContext();
        context.setKeyword(fileSearchPO.getKeyword());
        context.setUserId(UserIdUtil.get());
        String fileTypes = fileSearchPO.getFileTypes();
        if (StringUtils.isNotBlank(fileTypes) && !Objects.equals(FileConstants.ALL_FILE_TYPE, fileTypes)) {
            List<Integer> fileTypeArray = Splitter.on(RPanConstants.COMMON_SEPARATOR).splitToList(fileTypes).stream().map(Integer::valueOf).collect(Collectors.toList());
            context.setFileTypeArray(fileTypeArray);
        }
        List<FileSearchResultVO> result = iUserFileService.search(context);
        return R.data(result);
    }



    //value = "查询面包屑列表",
    @GetMapping("file/breadcrumbs")
    public R<List<BreadcrumbVO>> getBreadcrumbs(@NotBlank(message = "文件ID不能为空") @RequestParam(value = "fileId", required = false) String fileId) {
        QueryBreadcrumbsContext context = new QueryBreadcrumbsContext();
        context.setFileId(IdUtil.decrypt(fileId));
        context.setUserId(UserIdUtil.get());
        List<BreadcrumbVO> result = iUserFileService.getBreadcrumbs(context);
        return R.data(result);
    }


}
