package com.zzw.zpan.modules.file.po;


import com.zzw.zpan.utils.EntiyTranfer;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 秒传文件接口参数对象实体
 */
//("秒传文件接口参数对象实体")
@Data
public class SecUploadFilePO implements Serializable , EntiyTranfer {

    private static final long serialVersionUID = 5638531322051664526L;

    //(value = "秒传的父文件夹ID", required = true)
    @NotBlank(message = "父文件夹ID不能为空")
    private Long parentId;

    //(value = "文件名称", required = true)
    @NotBlank(message = "文件名称不能为空")
    private String filename;

    //(value = "文件的唯一标识", required = true)
    @NotBlank(message = "文件的唯一标识不能为空")
    private String identifier;

}
