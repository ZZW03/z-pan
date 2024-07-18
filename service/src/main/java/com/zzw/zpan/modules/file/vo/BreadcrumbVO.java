package com.zzw.zpan.modules.file.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zzw.zpan.modules.file.entity.RPanUserFile;
import com.zzw.zpan.serializer.IdEncryptSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

//("面包屑列表展示实体")
@Data
public class BreadcrumbVO implements Serializable {

    private static final long serialVersionUID = -6113151935665730951L;

    //("文件ID")
    @JsonSerialize(using = IdEncryptSerializer.class)
    private Long id;

    //("父文件夹ID")
    @JsonSerialize(using = IdEncryptSerializer.class)
    private Long parentId;

    //("文件夹名称")
    private String name;

    /**
     * 实体转换
     *
     * @param record
     * @return
     */
    public static BreadcrumbVO transfer(RPanUserFile record) {
        BreadcrumbVO vo = new BreadcrumbVO();

        if (Objects.nonNull(record)) {
            vo.setId(record.getFileId());
            vo.setParentId(record.getParentId());
            vo.setName(record.getFilename());
        }

        return vo;
    }

}
