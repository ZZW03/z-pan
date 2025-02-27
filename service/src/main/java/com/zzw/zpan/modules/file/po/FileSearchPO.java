package com.zzw.zpan.modules.file.po;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

//("文件搜索参数实体")
@Data
public class FileSearchPO implements Serializable {

    private static final long serialVersionUID = 5477817929836619174L;

    //(value = "搜索的关键字", required = true)
    @NotBlank(message = "搜索关键字不能为空")
    private String keyword;

    //(value = "文件类型，多个文件类型使用公用分隔符拼接")
    private String fileTypes;

}
