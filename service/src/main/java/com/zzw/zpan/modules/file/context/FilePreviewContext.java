package com.zzw.zpan.modules.file.context;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;


import java.io.Serializable;

/**
 * 文件预览的上下文实体对象
 */
@Data
public class FilePreviewContext implements Serializable {

    private static final long serialVersionUID = 4017444860148856342L;

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
