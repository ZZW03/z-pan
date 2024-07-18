package com.zzw.zpan.modules.file.po;


import com.zzw.zpan.utils.EntiyTranfer;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Data
public class CreateFolderPO implements Serializable, EntiyTranfer {

    private static final long serialVersionUID = 5475817231508440546L;

    //(value = "加密的父文件夹ID", required = true)
    @NotBlank(message = "父文件夹ID不能为空")
    private Long parentId;

    //(value = "文件夹名称", required = true)
    @NotBlank(message = "文件夹名称不能为空")
    private String folderName;

}
