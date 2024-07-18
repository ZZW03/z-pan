package com.zzw.zpan.modules.file.po;


import com.zzw.zpan.utils.EntiyTranfer;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 文件重命名参数对象
 */
@Data
//(value = "文件重命名参数对象")
public class UpdateFilenamePO implements Serializable, EntiyTranfer {

    private static final long serialVersionUID = -8138754986668154124L;

    //y(value = "更新的文件ID", required = true)
    @NotBlank(message = "更新的文件ID不能为空")
    private Long fileId;

    //(value = "新的文件名称", required = true)
    @NotBlank(message = "新的文件名称不能为空")
    private String newFilename;

}
