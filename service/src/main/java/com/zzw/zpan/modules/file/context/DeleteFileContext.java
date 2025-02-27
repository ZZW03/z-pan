package com.zzw.zpan.modules.file.context;

import com.zzw.zpan.utils.EntiyTranfer;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 批量删除文件上下文实体对象
 */
@Data
public class DeleteFileContext implements Serializable, EntiyTranfer {

    private static final long serialVersionUID = -5040051387091567725L;

    /**
     * 要删除的文件ID集合
     */
    private List<Long> fileIdList;

    /**
     * 当前的登录用户ID
     */
    private Long userId;

}
