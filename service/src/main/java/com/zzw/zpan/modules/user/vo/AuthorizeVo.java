package com.zzw.zpan.modules.user.vo;


import lombok.Data;

import java.util.Date;

@Data
public class AuthorizeVo {
    String token;
    String role;
    String name;
    Date CreateTime;
}
