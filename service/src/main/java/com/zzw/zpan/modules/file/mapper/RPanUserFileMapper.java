package com.zzw.zpan.modules.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.zzw.zpan.modules.file.context.FileSearchContext;
import com.zzw.zpan.modules.file.context.QueryFileListContext;
import com.zzw.zpan.modules.file.entity.RPanFile;
import com.zzw.zpan.modules.file.entity.RPanUserFile;
import com.zzw.zpan.modules.file.vo.FileSearchResultVO;
import com.zzw.zpan.modules.file.vo.RPanUserFileVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author imooc
 * @description 针对表【r_pan_user_file(用户文件信息表)】的数据库操作Mapper
 * @createDate 2022-11-09 18:36:41
 * @Entity com.imooc.pan.server.modules.file.entity.RPanUserFile
 */
@Mapper
public interface RPanUserFileMapper extends BaseMapper<RPanUserFile> {

    /**
     * 查询用户的文件列表
     *
     * @param context
     * @return
     */
    List<RPanUserFileVO> selectFileList(@Param("param") QueryFileListContext context);

    /**
     * 文件搜索
     *
     * @param context
     * @return
     */
    List<FileSearchResultVO> searchFile(@Param("param") FileSearchContext context);

}




