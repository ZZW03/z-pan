package com.zzw.zpan.modules.recycle.po;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

//("文件还原参数实体")
@Data
public class RestorePO implements Serializable {

    private static final long serialVersionUID = -8600005249933040664L;

    //(value = "要还原的文件ID集合，多个使用公用分割符分隔", required = true)
    @NotBlank(message = "请选择要还原的文件")
    private String fileIds;

}
