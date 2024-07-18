package com.zzw.zpan.modules.share.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.zzw.zpan.serializer.IdEncryptSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

//("分享者信息返回实体对象")
@Data
public class ShareUserInfoVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 5739630033108250153L;

    //("分享者的ID")
    @JsonSerialize(using = IdEncryptSerializer.class)
    private Long userId;

    //("分享者的名称")
    private String username;

}
