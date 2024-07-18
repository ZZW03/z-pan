package com.zzw.zpan.modules.share.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zzw.zpan.serializer.Date2StringSerializer;
import com.zzw.zpan.serializer.IdEncryptSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

//("分享链接列表结果实体对象")
@Data
public class RPanShareUrlListVO implements Serializable {

    private static final long serialVersionUID = -5301645564554502650L;

    //("分享的ID")
    @JsonSerialize(using = IdEncryptSerializer.class)
    private Long shareId;

    //("分享的名称")
    private String shareName;

    //("分享的URL")
    private String shareUrl;

    //("分享的分享码")
    private String shareCode;

    //("分享的状态")
    private Integer shareStatus;

    //("分享的类型")
    private Integer shareType;

    //("分享的过期类型")
    private Integer shareDayType;

    //("分享的过期时间")
    @JsonSerialize(using = Date2StringSerializer.class)
    private Date shareEndTime;

    //("分享的创建时间")
    @JsonSerialize(using = Date2StringSerializer.class)
    private Date createTime;

}
