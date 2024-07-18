package com.zzw.zpan.modules.share.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zzw.zpan.serializer.IdEncryptSerializer;
import lombok.Data;

import java.io.Serializable;

//("查询分享简单详情返回实体对象")
@Data
public class ShareSimpleDetailVO implements Serializable {

    private static final long serialVersionUID = -244174348108049506L;

    //("分享ID")
    @JsonSerialize(using = IdEncryptSerializer.class)
    private Long shareId;

    //("分享名称")
    private String shareName;

    //("分享人信息")
    private ShareUserInfoVO shareUserInfoVO;

}
