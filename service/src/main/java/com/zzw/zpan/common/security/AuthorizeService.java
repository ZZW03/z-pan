package com.zzw.zpan.common.security;

import com.zzw.zpan.modules.user.entity.RPanUser;
import com.zzw.zpan.modules.user.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AuthorizeService implements UserDetailsService {

    @Resource
    IUserService iUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //通过username 查询是否存在用户
        RPanUser accountByName = iUserService.findAccountByName(username);


        if(accountByName == null ){
            throw new UsernameNotFoundException("用户名没有注册");
        }

        //返回信息后 进行密码对比
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                //密码一定要加密才行
                .password(accountByName.getPassword())
                .roles(accountByName.getUserRole())
                .build();
    }

}
