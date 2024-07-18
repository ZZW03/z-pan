package com.zzw.zpan.modules.share.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ShareErrorCode {

    SHARE_CODE_ALREADY_EXP(10000,"验证码已经失效"),

    SHARE_CODE_ERROR(10001,"验证码错误");


    private final Integer code;

    private final String messages;

}
