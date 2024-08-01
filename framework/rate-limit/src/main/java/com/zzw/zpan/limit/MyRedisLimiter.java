package com.zzw.zpan.limit;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MyRedisLimiter {
    /**
     * 缓存到Redis的key
     */
    String key();
    /**
     * Key的前缀
     */
    String prefix() default "limiter:";
    /**
     * 给定的时间范围 单位(秒)
     * 默认1秒 即1秒内超过count次的请求将会被限流
     */
    int period() default 1;
    /**
     * 一定时间内最多访问的次数
     */
    int count();


}