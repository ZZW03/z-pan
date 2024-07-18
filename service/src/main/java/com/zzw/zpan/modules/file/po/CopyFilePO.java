package com.zzw.zpan.modules.file.po;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

//("文件复制参数实体对象")
@Data
public class CopyFilePO implements Serializable {

    private static final long serialVersionUID = 5713445035865802696L;

    //("要复制的文件ID集合，多个使用公用分隔符隔开")
    @NotBlank(message = "请选择要复制的文件")
    private String fileIds;

    //("要转移到的目标文件夹的ID")
    @NotBlank(message = "请选择要转移到哪个文件夹下面")
    private String targetParentId;

}
