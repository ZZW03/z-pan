package com.zzw.zpan.modules.recycle.service;



import com.zzw.zpan.modules.file.vo.RPanUserFileVO;
import com.zzw.zpan.modules.recycle.context.DeleteContext;
import com.zzw.zpan.modules.recycle.context.QueryRecycleFileListContext;
import com.zzw.zpan.modules.recycle.context.RestoreContext;

import java.util.List;

public interface IRecycleService {

    /**
     * 查询用户的回收站文件列表
     *
     * @param context
     * @return
     */
    List<RPanUserFileVO> recycles(QueryRecycleFileListContext context);

    /**
     * 文件还原
     *
     * @param context
     */
    void restore(RestoreContext context);

    /**
     * 文件彻底删除
     *
     * @param context
     */
    void delete(DeleteContext context);

}
