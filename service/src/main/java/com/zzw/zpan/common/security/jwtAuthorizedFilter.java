package com.zzw.zpan.common.security;

import cn.hutool.core.util.IdUtil;
import com.zzw.utils.JwtUtils;
import com.zzw.zpan.common.utils.UserIdUtil;
import com.zzw.zpan.modules.user.service.IUserService;
import com.zzw.zpan.utils.Const;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class jwtAuthorizedFilter extends OncePerRequestFilter {


    @Resource
    JwtUtils util;

    @Lazy
    @Resource
    IUserService iUserService;

    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {



        String Authorization = request.getHeader("Authorization");

            if (Authorization !=null && Authorization.startsWith("Bearer ")){

                // 过滤器前面的 "Bearer "
                String token = Authorization.substring(7);
                //开始解析数据
                UserDetails user = util.verifyJWT(token);
                Long idByName = iUserService.getIdByName(user.getUsername());
                UserIdUtil.set(idByName);

                if(user!=null){
                    // 创建Authentication对象
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // 将Authentication对象设置到SecurityContext中
                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                    request.setAttribute(Const.ATTR_USER_Name,user.getUsername());
                    request.setAttribute(Const.ATTR_USER_ROLE,new ArrayList<>(user.getAuthorities()).get(0).getAuthority());
                }
            }


        // 继续传递请求给下一个过滤器
        filterChain.doFilter(request, response);
    }
}
