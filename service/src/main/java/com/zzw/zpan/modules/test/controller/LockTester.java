package com.zzw.zpan.modules.test.controller;

import com.zzw.zpan.core.annotation.Lock;
import org.springframework.stereotype.Component;

@Component
public class LockTester {

    @Lock(name = "test", keys = "#name", expireSecond = 10L)
    public String testLock(String name) {
        System.out.println(Thread.currentThread().getName() + " get the lock.");
        String result = "hello " + name;
        System.out.println(Thread.currentThread().getName() + " release the lock.");
        return result;
    }

}
