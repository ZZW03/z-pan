package com.zzw.zpan.modules.file.po;


import com.zzw.zpan.utils.EntiyTranfer;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

//("文件分片上传参数实体")
@Data
public class FileChunkUploadPO implements Serializable, EntiyTranfer {

    private static final long serialVersionUID = 8036267299049093753L;

    //(value = "文件名称", required = true)
    @NotBlank(message = "文件名称不能为空")
    private String filename;

    //(value = "文件唯一标识", required = true)
    @NotBlank(message = "文件唯一标识不能为空")
    private String identifier;

    //(value = "总体的分片数", required = true)
    @NotNull(message = "总体的分片数不能为空")
    private Integer totalChunks;

    //(value = "当前分片的下标", required = true)
    @NotNull(message = "当前分片的下标不能为空")
    private Integer chunkNumber;

    //(value = "当前分片的大小", required = true)
    @NotNull(message = "当前分片的大小不能为空")
    private Long currentChunkSize;

    //(value = "文件总大小", required = true)
    @NotNull(message = "文件总大小不能为空")
    private Long totalSize;

    //(value = "分片文件实体", required = true)
    @NotNull(message = "分片文件实体不能为空")
    private MultipartFile file;

}
