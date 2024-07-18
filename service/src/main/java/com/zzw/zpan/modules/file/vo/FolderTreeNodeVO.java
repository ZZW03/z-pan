package com.zzw.zpan.modules.file.vo;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zzw.zpan.serializer.IdEncryptSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

//("文件夹树节点实体")
@Data
public class FolderTreeNodeVO implements Serializable {

    private static final long serialVersionUID = -2736575645065574820L;

    //("文件夹名称")
    private String label;

    //("文件ID")
    @JsonSerialize(using = IdEncryptSerializer.class)
    private Long id;

    //("父文件ID")
    @JsonSerialize(using = IdEncryptSerializer.class)
    private Long parentId;

    //("子节点集合")
    private List<FolderTreeNodeVO> children;

    public void print() {
        String jsonString = JSON.toJSONString(this);
        System.out.println(jsonString);
    }

}
