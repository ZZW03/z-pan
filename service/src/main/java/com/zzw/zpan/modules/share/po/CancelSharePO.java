package com.zzw.zpan.modules.share.po;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

//("取消分享参数实体对象")
@Data
public class CancelSharePO implements Serializable {

    private static final long serialVersionUID = -1536161974548607677L;

    //(value = "要取消的分享ID的集合，多个使用公用的分割符拼接", required = true)
    @NotBlank(message = "请选择要取消的分享")
    private String shareIds;

}
