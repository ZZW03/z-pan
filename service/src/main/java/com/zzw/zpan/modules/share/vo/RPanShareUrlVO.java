package com.zzw.zpan.modules.share.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zzw.zpan.serializer.IdEncryptSerializer;
import lombok.Data;

import java.io.Serializable;

//(value = "创建分享链接的返回实体对象")
@Data
public class RPanShareUrlVO implements Serializable {

    private static final long serialVersionUID = 3468789641541361147L;

    @JsonSerialize(using = IdEncryptSerializer.class)
    //("分享链接的ID")
    private Long shareId;

    //("分享链接的名称")
    private String shareName;

    //("分享链接的URL")
    private String shareUrl;

    //("分享链接的分享码")
    private String shareCode;

    //("分享链接的状态")
    private Integer shareStatus;

}
