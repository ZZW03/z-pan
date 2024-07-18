package com.zzw.zpan.common.security;

import com.zzw.zpan.log.HttpLogFilter;
import com.zzw.zpan.modules.user.entity.RPanUser;
import com.zzw.zpan.modules.user.service.IUserService;
import com.zzw.zpan.modules.user.vo.AuthorizeVo;
import com.zzw.zpan.response.R;
import org.springframework.context.annotation.Configuration;

import com.zzw.utils.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@Configuration
public class securityConfiguration {

    @Resource
    jwtAuthorizedFilter jwtAuthorizedFilter;

    @Resource
    HttpLogFilter httpLogFilter;

    @Resource
    JwtUtils jwtUtils;

    @Lazy
    @Resource
    IUserService iUserService;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(config -> config
                        .anyRequest().authenticated()
                )
                .formLogin(conf -> {
                    conf.loginProcessingUrl("/api/auth/login");
                    //使用自定义的成功失败处理器
                    conf.failureHandler(this::onAuthenticationFailure);
                    conf.successHandler(this::onAuthenticationSuccess);
                    conf.permitAll();
                })
                .logout(conf -> conf
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler(this::onLogoutSuccess)

                )

                .exceptionHandling(conf->conf
                        .authenticationEntryPoint(this::commence)
                        .accessDeniedHandler(this::onHandle))

                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(conf -> {
                    conf.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilterBefore(jwtAuthorizedFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(httpLogFilter,UsernamePasswordAuthenticationFilter.class)

                .build();

    }

    //登录失败处理器
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(R.fail(401,"登录失败，密码错误").ToJSON());


    }

    //登录成功处理器
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {

        response.setContentType("application/json;charset=utf-8");
        User user = (User) authentication.getPrincipal();

        RPanUser panUser = iUserService.findAccountByName(user.getUsername());
        String token = jwtUtils.createJWT(user);
        AuthorizeVo authorizeVo = panUser.asViewObject(AuthorizeVo.class,o->{
            o.setName(panUser.getUsername());
            o.setRole(panUser.getUserRole());
            o.setToken(token);
            o.setCreateTime(panUser.getCreateTime());
        });


        //返回给前端 在前端进行存储
        //然后前端进行请求的时候 通过添加请求头的方式 再放回
        response.getWriter().write(R.data(authorizeVo).ToJSON());
    }
    //未登录的报错
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(R.fail("请先登录").ToJSON());
    }
    //没权限登录
    public void onHandle(HttpServletRequest request,
                         HttpServletResponse response,
                         AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(R.fail("您没有权限").ToJSON());
    }
    //退出成功
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        String Header = request.getHeader("Authorization");


        if(jwtUtils.invalidateJwt(Header)){
            writer.write(R.success("退出成功").ToJSON());
        }else{
            writer.write(R.fail(403,"退出失败").ToJSON());
        }
    }
}
