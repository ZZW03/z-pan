package com.zzw.zpan.modules.share.vo;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zzw.zpan.modules.file.vo.RPanUserFileVO;
import com.zzw.zpan.serializer.Date2StringSerializer;
import com.zzw.zpan.serializer.IdEncryptSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

//("分享详情的返回实体对象")
@Data
public class ShareDetailVO implements Serializable {

    private static final long serialVersionUID = -2446579294335071804L;

    @JsonSerialize(using = IdEncryptSerializer.class)
    //("分享的ID")
    private Long shareId;

    //("分享的名称")
    private String shareName;

    //("分享的创建时间")
    @JsonSerialize(using = Date2StringSerializer.class)
    private Date createTime;

    //("分享的过期类型")
    private Integer shareDay;

    //("分享的截止时间")
    @JsonSerialize(using = Date2StringSerializer.class)
    private Date shareEndTime;

    //("分享的文件列表")
    private List<RPanUserFileVO> rPanUserFileVOList;

    //("分享者的信息")
    private ShareUserInfoVO shareUserInfoVO;

}
