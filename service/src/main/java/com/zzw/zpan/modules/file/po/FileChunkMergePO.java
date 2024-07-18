package com.zzw.zpan.modules.file.po;


import com.zzw.zpan.utils.EntiyTranfer;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

//("文件分片合并参数对象")
@Data
public class FileChunkMergePO implements Serializable, EntiyTranfer {

    private static final long serialVersionUID = 1415904752976870786L;

    //(value = "文件名称", required = true)
    @NotBlank(message = "文件名称不能为空")
    private String filename;

    //(value = "文件唯一标识", required = true)
    @NotBlank(message = "文件唯一标识不能为空")
    private String identifier;

    //(value = "文件总大小", required = true)
    @NotNull(message = "文件总大小不能为空")
    private Long totalSize;

    //(value = "文件的父文件夹ID", required = true)
    @NotBlank(message = "文件的父文件夹ID不能为空")
    private Long parentId;

}
