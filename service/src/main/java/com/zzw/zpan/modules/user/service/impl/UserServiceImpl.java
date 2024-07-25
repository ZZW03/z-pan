package com.zzw.zpan.modules.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.j2objc.annotations.ReflectionSupport;
import com.zzw.zpan.exception.RPanBusinessException;
import com.zzw.zpan.modules.file.constants.FileConstants;
import com.zzw.zpan.modules.file.context.CreateFolderContext;
import com.zzw.zpan.modules.file.service.IUserFileService;
import com.zzw.zpan.modules.user.context.UserRegisterContext;
import com.zzw.zpan.modules.user.entity.RPanUser;
import com.zzw.zpan.modules.user.mapper.RPanUserMapper;
import com.zzw.zpan.modules.user.service.IUserService;
import com.zzw.zpan.response.ResponseCode;
import com.zzw.zpan.utils.IdUtil;
import com.zzw.zpan.utils.PasswordUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Objects;

/**
 * @author imooc
 * @description 针对表【r_pan_user(用户信息表)】的数据库操作Service实现
 * @createDate 2022-11-09 18:34:37
 */
@Service
public class UserServiceImpl extends ServiceImpl<RPanUserMapper, RPanUser> implements IUserService {

    @Resource
    IUserFileService iUserFileService;


    @Resource
    PasswordEncoder passwordEncoder;

    /**
     * 用户注册的业务实现
     * 需要实现的功能点：
     * 1、注册用户信息
     * 2、创建新用户的根本目录信息
     * <p>
     * 需要实现的技术难点：
     * 1、该业务是幂等的
     * 2、要保证用户名全局唯一
     * <p>
     * 实现技术难点的处理方案：
     * 1、幂等性通过数据库表对于用户名字段添加唯一索引，我们上有业务捕获对应的冲突异常，转化返回
     *
     * @param userRegisterContext
     * @return
     */
    @Override
    public Long register(UserRegisterContext userRegisterContext) {
        assembleUserEntity(userRegisterContext);
        doRegister(userRegisterContext);
        createUserRootFolder(userRegisterContext);
        return userRegisterContext.getEntity().getUserId();
    }

    @Override
    public RPanUser findAccountByName(String username) {
       return query().eq("username",username)
               .one();
    }

    @Override
    public Long getIdByName(String username) {
        return query().eq("username",username)
                .one().getUserId();
    }


    /************************************************private************************************************/



    /**
     * 创建用户的根目录信息
     *
     * @param userRegisterContext
     */
    private void createUserRootFolder(UserRegisterContext userRegisterContext) {
        CreateFolderContext createFolderContext = new CreateFolderContext();
        createFolderContext.setParentId(FileConstants.TOP_PARENT_ID);
        createFolderContext.setUserId(userRegisterContext.getEntity().getUserId());
        createFolderContext.setFolderName(FileConstants.ALL_FILE_CN_STR);
        iUserFileService.createFolder(createFolderContext);
    }

    /**
     * 实现注册用户的业务
     * 需要捕获数据库的唯一索引冲突异常，来实现全局用户名称唯一
     *
     * @param userRegisterContext
     */
    private void doRegister(UserRegisterContext userRegisterContext) {
        RPanUser entity = userRegisterContext.getEntity();
        if (Objects.nonNull(entity)) {
            try {
                if (!save(entity)) {
                    throw new RPanBusinessException("用户注册失败");
                }
            } catch (DuplicateKeyException duplicateKeyException) {
                throw new RPanBusinessException("用户名已存在");
            }
            return;
        }
        throw new RPanBusinessException(ResponseCode.ERROR);
    }

    /**
     * 实体转化
     * 由上下文信息转化成用户实体，封装进上下文
     *
     * @param userRegisterContext
     */
    private void assembleUserEntity(UserRegisterContext userRegisterContext) {
        RPanUser entity = userRegisterContext.asViewObject(RPanUser.class,o->{
            o.setUsername(userRegisterContext.getUsername());
            o.setAnswer(userRegisterContext.getAnswer());
            o.setQuestion(userRegisterContext.getQuestion());
            o.setPassword(passwordEncoder.encode(userRegisterContext.getPassword()));
        });

        String salt = PasswordUtil.getSalt();
        entity.setUserId(IdUtil.get());
        entity.setSalt(salt);
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        userRegisterContext.setEntity(entity);
    }





}




