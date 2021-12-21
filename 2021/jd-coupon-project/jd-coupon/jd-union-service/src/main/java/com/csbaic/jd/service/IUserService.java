package com.csbaic.jd.service;

import com.csbaic.jd.dto.*;
import com.csbaic.jd.entity.UserEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.csbaic.jd.enums.UserIdentify;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-14
 */
public interface IUserService extends IService<UserEntity> {

    /**
     * 使用微信注册用户
     * @param user
     * @return
     */
     LoginInfo createUserByWechat(CreateWechatUser user);

    /**
     * 使用邀请码获取使用信息
     * @param code
     * @return
     */
     SimpleUserInfo getUserInfoByInvitationCode(String code);


    /**
     * 更新用户信息
     * @param userId
     * @param updateUserInfo
     */
     void updateUserInfo(Long userId, UpdateUserInfo updateUserInfo);


    /**
     * 获取我的用户信息
     * @param userId
     * @return
     */
     MineUserInfo getMyUserInfo(Long userId);


    /**
     * 获取用户的等级
     * @param userId
     * @return
     */
    UserIdentify getUserIdentifyById(Long userId);
}
