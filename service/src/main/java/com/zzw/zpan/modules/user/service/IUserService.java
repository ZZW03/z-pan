package com.zzw.zpan.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzw.zpan.modules.user.context.UserRegisterContext;
import com.zzw.zpan.modules.user.entity.RPanUser;


/**
 * @author imooc
 * @description 针对表【r_pan_user(用户信息表)】的数据库操作Service
 * @createDate 2022-11-09 18:34:37
 */
public interface IUserService extends IService<RPanUser> {


    /**
     * 用户注册业务
     * @param userRegisterContext
     * @return
     */
    Long register(UserRegisterContext userRegisterContext);


    RPanUser findAccountByName(String username);

    Long getIdByName(String username);
}
