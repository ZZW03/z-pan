package com.zzw.zpan.modules.test.controller;

import com.zzw.zpan.response.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {


    @GetMapping("/hello")
    public String hello(){
        return R.data("hello").ToJSON();
    }


}
