package com.zzw.zpan.modules.file.po;


import com.zzw.zpan.utils.EntiyTranfer;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

//(value = "单文件上传参数实体对象")
@Data
public class FileUploadPO implements Serializable, EntiyTranfer {

    private static final long serialVersionUID = -7944685825604968904L;

    //(value = "文件名称", required = true)
    @NotBlank(message = "文件名称不能为空")
    private String filename;

    //(value = "文件的唯一标识", required = true)
    @NotBlank(message = "文件的唯一标识不能为空")
    private String identifier;

//    //(value = "文件的总大小", required = true)
//    @NotNull(message = "文件的总大小不能为空")
//    private Long totalSize;

    //(value = "文件的父文件夹ID", required = true)
    @NotBlank(message = "文件的父文件夹ID不能为空")
    private Long parentId;

    //(value = "文件实体", required = true)
    @NotNull(message = "文件实体不能为空")
    private MultipartFile file;

}
