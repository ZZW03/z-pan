package com.zzw.zpan.modules.file.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

//("查询用户已上传的文件分片列表返回实体")
@Data
public class UploadedChunksVO implements Serializable {

    private static final long serialVersionUID = 8694674586602329820L;

    //("已上传的分片编号列表")
    private List<Integer> uploadedChunks;

}
