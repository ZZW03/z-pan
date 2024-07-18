package com.zzw.zpan.modules.file.po;


import com.zzw.zpan.utils.EntiyTranfer;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Data
public class DeleteFilePO implements Serializable, EntiyTranfer {

    private static final long serialVersionUID = 3098611201745909528L;

    //(value = "要删除的文件ID，多个使用公用的分隔符分割", required = true)
    @NotBlank(message = "请选择要删除的文件信息")
    private String fileIds;

}
