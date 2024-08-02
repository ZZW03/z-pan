package com.zzw.zpan.modules.test.controller;

import com.alibaba.otter.canal.client.CanalConnector;
import com.zzw.zpan.limit.MyRedisLimiter;
import com.zzw.zpan.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

    @Autowired
    @Qualifier("promotionConnector")
    private CanalConnector connector;


    @GetMapping("/hello")
//    @MyRedisLimiter(key = "hhh",count = 10,period = 100,prefix = "hhh")
    public String hello(){
        System.out.println(connector.checkValid());
        return R.data("hello").ToJSON();
    }



}
