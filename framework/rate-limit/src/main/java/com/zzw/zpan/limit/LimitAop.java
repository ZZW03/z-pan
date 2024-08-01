package com.zzw.zpan.limit;

import com.zzw.zpan.exception.RPanBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.Collections;

import static com.zzw.zpan.limit.configuration.redisScript;



@Slf4j
@Aspect
@Component
public class LimitAop {



        @Autowired
        @Qualifier("LimitRedis")
        private RedisTemplate<String, Integer> limitRedisTemplate;

        @Around("@annotation(MyRedisLimiter)")
        public Object doInterceptor(ProceedingJoinPoint joinPoint, MyRedisLimiter MyRedisLimiter) throws Throwable {

                log.info("进入限流拦截器");

                String key = MyRedisLimiter.key();
                int counts = MyRedisLimiter.count();
                int period = MyRedisLimiter.period();
                String prefix = MyRedisLimiter.prefix();
                String concat = this.concat(prefix, key);

                Number count = limitRedisTemplate.execute(redisScript,
                        Collections.singletonList(concat),
                        counts,  // 确保 counts 是字符串
                        period);  // 确保 period 是字符串


            System.out.println(count);
                if (count.intValue() > counts){
                    throw new RPanBusinessException();
                }

                return joinPoint.proceed();
        }

        private String concat(String prefix, String key) {
            return prefix + key;
        }
}


