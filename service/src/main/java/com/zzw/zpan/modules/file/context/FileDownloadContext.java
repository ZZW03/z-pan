package com.zzw.zpan.modules.file.context;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;


import java.io.Serializable;

/**
 * 文件下载的上下文实体对象
 */
@Data
public class FileDownloadContext implements Serializable {

    private static final long serialVersionUID = -8571038899400767013L;

    /**
     * 文件ID
     */
    private Long fileId;

    /**
     * 请求响应对象
     */
    private HttpServletResponse response;

    /**
     * 当前登录的用户ID
     */
    private Long userId;

}
