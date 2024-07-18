package com.zzw.zpan.modules.file.vo;


import lombok.Data;

import java.io.Serializable;

//("文件分片上传的响应实体")
@Data
public class FileChunkUploadVO implements Serializable {

    private static final long serialVersionUID = 7670192129580713809L;

    //("是否需要合并文件 0 不需要 1 需要")
    private Integer mergeFlag;

}
