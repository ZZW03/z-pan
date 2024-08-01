package com.zzw.zpan.limit;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.io.IOException;

@Slf4j
@Configuration
public class configuration {

    private static final String LIMIT_LUA_PATH = "limit.lua";
    public static DefaultRedisScript<Integer> redisScript;


    @PostConstruct
    public void init() {
        redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Integer.class);
        ClassPathResource classPathResource = new ClassPathResource(LIMIT_LUA_PATH);
        try {
            classPathResource.getInputStream();//探测资源是否存在
            redisScript.setScriptSource(new ResourceScriptSource(classPathResource));
            log.info("luaScript init success");
        } catch (IOException e) {
            log.error("luaScript init fail");
        }
    }

}
