package com.zzw.zpan.modules.file.po;


import com.zzw.zpan.utils.EntiyTranfer;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

//("查询用户已上传分片列表的参数实体")
@Data
public class QueryUploadedChunksPO implements Serializable, EntiyTranfer {

    private static final long serialVersionUID = 866722676187500143L;

    //(value = "文件的唯一标识", required = true)
    @NotBlank(message = "文件唯一标识不能为空")
    private String identifier;

}
