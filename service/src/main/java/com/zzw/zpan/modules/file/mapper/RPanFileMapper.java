package com.zzw.zpan.modules.file.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzw.zpan.modules.file.entity.RPanFile;
import org.apache.ibatis.annotations.Mapper;

/**
* @author imooc
* @description 针对表【r_pan_file(物理文件信息表)】的数据库操作Mapper
* @createDate 2022-11-09 18:36:41
* @Entity com.imooc.pan.server.modules.file.entity.RPanFile
*/
@Mapper
public interface RPanFileMapper extends BaseMapper<RPanFile> {

}




