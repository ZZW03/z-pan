package com.zzw.zpan.modules.file.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;;
import com.zzw.zpan.serializer.Date2StringSerializer;
import com.zzw.zpan.serializer.IdEncryptSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户搜索文件列表相应实体
 */
@Data
//(value = "用户搜索文件列表相应实体")
public class FileSearchResultVO implements Serializable {

    private static final long serialVersionUID = 3317950091518504175L;

    @JsonSerialize(using = IdEncryptSerializer.class)
    //(value = "文件ID")
    private Long fileId;

    @JsonSerialize(using = IdEncryptSerializer.class)
    //(value = "父文件夹ID")
    private Long parentId;

    //(value = "父文件夹名称")
    private String parentFilename;

    //(value = "文件名称")
    private String filename;

    //(value = "文件大小描述")
    private String fileSizeDesc;

    //(value = "文件夹标识 0 否 1 是")
    private Integer folderFlag;

    //(value = "文件类型 1 普通文件 2 压缩文件 3 excel 4 word 5 pdf 6 txt 7 图片 8 音频 9 视频 10 ppt 11 源码文件 12 csv")
    private Integer fileType;

    //(value = "文件更新时间")
    @JsonSerialize(using = Date2StringSerializer.class)
    private Date updateTime;

}
