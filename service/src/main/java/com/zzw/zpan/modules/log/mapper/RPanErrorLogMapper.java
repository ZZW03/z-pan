package com.zzw.zpan.modules.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzw.zpan.modules.log.entity.RPanErrorLog;
import org.apache.ibatis.annotations.Mapper;

/**
* @author imooc
* @description 针对表【r_pan_error_log(错误日志表)】的数据库操作Mapper
* @createDate 2022-11-09 18:37:48
* @Entity com.imooc.pan.server.modules.log.entity.RPanErrorLog
*/
@Mapper
public interface RPanErrorLogMapper extends BaseMapper<RPanErrorLog> {

}




