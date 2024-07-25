package com.zzw.zpan.common.aop;


import com.zzw.zpan.common.annotation.needCode;
import com.zzw.zpan.common.utils.ShareIdUtil;
import com.zzw.zpan.common.utils.ShareTokenUtil;
import com.zzw.zpan.exception.RPanBusinessException;
import com.zzw.zpan.modules.share.enums.ShareErrorCode;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class CodeInterceptor {

    @Resource
    private HttpServletRequest request;

    @Around("@annotation(needCode)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint,
                                needCode needCode) throws Throwable {
        String string = request.getAttribute("Share-code").toString();
        Long ShareId = ShareTokenUtil.verifyShareToken(string);
        if (ShareId == null){
            return new RPanBusinessException(ShareErrorCode.SHARE_CODE_ALREADY_EXP.getCode(),
                    ShareErrorCode.SHARE_CODE_ALREADY_EXP.getMessages());
        }
        ShareIdUtil.set(ShareId);
        return joinPoint.proceed();
    }


}
