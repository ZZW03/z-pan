package com.zzw.zpan.modules.share.context;


import com.zzw.zpan.modules.share.entity.RPanShare;
import com.zzw.zpan.modules.share.vo.ShareSimpleDetailVO;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询分享简单详情上下文实体信息
 */
@Data
public class QueryShareSimpleDetailContext implements Serializable {

    /**
     * 分享的ID
     */
    private Long shareId;

    /**
     * 分享对应的实体信息
     */
    private RPanShare record;

    /**
     * 简单分享详情的VO对象
     */
    private ShareSimpleDetailVO vo;

}
