package com.zzw.zpan.common.utils;


import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.zzw.zpan.exception.RPanBusinessException;
import com.zzw.zpan.modules.share.constants.ShareConstants;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class ShareTokenUtil {

    static {
        signer = JWTSignerUtil.hs256(ShareConstants.JWT_KEY.getBytes());
    }

    private static final JWTSigner signer;


    public static String generateCokeShareToken(Long Id){

        Date now = DateUtil.date(Calendar.getInstance());
        Date newDate = DateUtil.offset(now, DateField.HOUR_OF_DAY, 1);

        String token = String.valueOf(JWT.create().setJWTId(UUID.randomUUID().toString())
                .setPayload(ShareConstants.SHARE_ID, Id)
                .setExpiresAt(newDate).setIssuedAt(now)
                .setSigner(signer).sign());


        return token;
    }

    public static Long verifyShareToken(String token){
        try {
            JWTValidator.of(token).validateDate(DateUtil.date()).validateDate();
            JWT jwt = JWT.of(token);
            return (Long) jwt.getPayload(ShareConstants.SHARE_ID);
        } catch (Exception var3) {
           throw  new RPanBusinessException("验证过期");
        }
    }


}
