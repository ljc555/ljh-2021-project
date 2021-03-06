package com.csbaic.jd.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csbaic.auth.accesstoken.AccessTokenService;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.common.result.ResultCode;
import com.csbaic.jd.dto.*;
import com.csbaic.jd.entity.OptionEntity;
import com.csbaic.jd.entity.UserEntity;
import com.csbaic.jd.entity.WechatUserEntity;
import com.csbaic.jd.enums.UserIdentify;
import com.csbaic.jd.mapper.UserMapper;
import com.csbaic.jd.service.IOptionService;
import com.csbaic.jd.service.ITeamMemberService;
import com.csbaic.jd.service.IUserService;
import com.csbaic.jd.service.IWechatUserService;
import com.csbaic.jd.service.login.WechatUser;
import com.csbaic.jd.service.login.WechatUserRepository;
import com.csbaic.jd.service.option.Options;
import com.csbaic.jd.service.team.InvitationCodeGenerator;
import com.csbaic.jd.service.user.active.ActiveScoreCalculator;
import com.csbaic.jd.service.user.active.ActiveScoreRequest;
import com.csbaic.jd.utils.UserUtils;
import com.csbaic.jd.web.ResultCodes;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService {


    @Autowired
    private InvitationCodeGenerator invitationCodeGenerator;


    @Autowired
    private AccessTokenService accessTokenService;



    @Autowired
    private ITeamMemberService teamMemberService;

    @Autowired
    private IWechatUserService wechatUserService;

    @Autowired
    private WxMaService wxMaService;

    @Autowired
    private WechatUserRepository wechatUserRepository;

    @Autowired
    private ActiveScoreCalculator activeScoreCalculator;

    @Autowired
    private IOptionService optionService;

    @Transactional
    @Override
    public LoginInfo createUserByWechat(CreateWechatUser user) {

        if (user == null) {
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "????????????");
        }


        if (Strings.isNullOrEmpty(user.getPhone())) {
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "???????????????????????????");
        }


        if (Strings.isNullOrEmpty(user.getInvitationCode())) {
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "?????????????????????");
        }


        if (Strings.isNullOrEmpty(user.getNickName())) {
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "????????????????????????");
        }


        if (Strings.isNullOrEmpty(user.getToken())) {
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "Token????????????");
        }


        if (Strings.isNullOrEmpty(user.getAvatarUrl())) {
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "????????????????????????");
        }

        UserEntity parentUser = getOne(
                Wrappers.<UserEntity>query()
                        .eq(UserEntity.INVITATION_CODE, user.getInvitationCode())
        );

        if (parentUser == null) {
            throw BizRuntimeException.from(ResultCodes.INVALID_INVITATION_CODE);
        }


        //??????????????????
        WechatUser wechatUser = wechatUserRepository.get(user.getToken());

        /*
            ?????????????????????????????????????????????login?????????????????????openId???sessionKey???
            ????????????openId???????????????????????????????????????
         */
        if (wechatUser == null) {
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "???????????????????????????????????????");
        }

        //??????openId?????????????????????
        if(wechatUserService.count(
                Wrappers.<WechatUserEntity>query()
                        .eq(WechatUserEntity.OPEN_ID, user.getToken())
        ) > 0){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "???????????????????????????");
        }


        WxMaPhoneNumberInfo wxMaPhoneNumberInfo = wxMaService.getUserService().getPhoneNoInfo(wechatUser.getSessionKey(), user.getPhone(), user.getIv());
        if (wxMaPhoneNumberInfo == null || Strings.isNullOrEmpty(wxMaPhoneNumberInfo.getPurePhoneNumber())) {
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "???????????????????????????");
        }

        int defIdentify = optionService.getInt(Options.OPT_SYS_REGISTER_USER_IDENTIFY);

        UserEntity userEntity = new UserEntity();
        userEntity.setPhone(wxMaPhoneNumberInfo.getPurePhoneNumber());
        userEntity.setNickName(user.getNickName());
        userEntity.setAvatarUrl(user.getAvatarUrl());
        userEntity.setIdentify(defIdentify);
        userEntity.setInviterId(parentUser.getId());
        save(userEntity);

        userEntity.setInvitationCode(invitationCodeGenerator.generate(userEntity.getId()));
        saveOrUpdate(userEntity);


        //??????????????????
        teamMemberService.addMember(parentUser.getId(), userEntity.getId());


        //??????????????????
        WechatUserEntity wechatUserEntity = new WechatUserEntity();
        wechatUserEntity.setOpenId(wechatUser.getOpenId());
        wechatUserEntity.setSessionKey(wechatUser.getSessionKey());
        wechatUserEntity.setUnionId(wechatUser.getUnionId());
        wechatUserEntity.setUserId(userEntity.getId());
        wechatUserService.save(wechatUserEntity);


        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userEntity.getId());

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setToken(accessTokenService.encode(params));
        loginInfo.setPhone(userEntity.getPhone());
        loginInfo.setNickName(userEntity.getNickName());
        loginInfo.setAvatarUrl(userEntity.getAvatarUrl());
        loginInfo.setUserId(userEntity.getId());

        return loginInfo;
    }

    @Override
    public SimpleUserInfo getUserInfoByInvitationCode(String code) {
        if (Strings.isNullOrEmpty(code)) {
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "??????????????????");
        }

        UserEntity userEntity = getOne(
                Wrappers.<UserEntity>
                        query().eq(UserEntity.INVITATION_CODE, code)
        );

        if (userEntity == null) {
            throw BizRuntimeException.from(ResultCode.NOT_FOUND, "?????????????????????" + code);
        }

        SimpleUserInfo userInfo = new SimpleUserInfo();
        BeanUtils.copyProperties(userEntity, userInfo);

        return userInfo;
    }


    @Override
    @Transactional
    public void updateUserInfo(Long userId, UpdateUserInfo updateUserInfo) {
        if(userId == null || updateUserInfo == null){
            return;
        }

        Map<String , Object> values = new HashMap<>();
        if(!Strings.isNullOrEmpty(updateUserInfo.getAvatarUrl())){
            values.put(UserEntity.AVATAR_URL, updateUserInfo.getAvatarUrl());
        }

        if(!Strings.isNullOrEmpty(updateUserInfo.getNickName())){
            values.put(UserEntity.NICKNAME, updateUserInfo.getNickName());
        }

        if(!Strings.isNullOrEmpty(updateUserInfo.getPhone())){
            values.put(UserEntity.PHONE, updateUserInfo.getPhone());
        }

        log.debug("Update user {}", values);
        if(!values.isEmpty()){
           getBaseMapper().updateUserInfoById(userId, values);
        }

        log.debug("Update wechat  {}", updateUserInfo.getWechatId());
        if(!Strings.isNullOrEmpty(updateUserInfo.getWechatId())){
            getBaseMapper().updateWechatIdById(userId, updateUserInfo.getWechatId());
        }

    }

    @Override
    public MineUserInfo getMyUserInfo(Long userId) {

        if(userId == null){
            throw BizRuntimeException.from(ResultCode.ERROR, "???????????????");
        }

        UserEntity userEntity = getById(userId);

        if(userEntity == null){
            throw BizRuntimeException.from(ResultCode.NOT_FOUND, "???????????????");
        }

        WechatUserEntity wechatUserEntity = wechatUserService.getOne(
                Wrappers.<WechatUserEntity>query()
                .eq(WechatUserEntity.USER_ID, userEntity.getId())
        );


        MineUserInfo mineUserInfo = new MineUserInfo();
        mineUserInfo.setUserId(userEntity.getId());
        mineUserInfo.setNickName(userEntity.getNickName());
        mineUserInfo.setAvatarUrl(userEntity.getAvatarUrl());
        mineUserInfo.setIdentify(userEntity.getIdentify());
        mineUserInfo.setIdentifyDesc(UserUtils.getUserIdentify(userEntity.getIdentify()).getName());
        mineUserInfo.setInvitationCode(userEntity.getInvitationCode());
        mineUserInfo.setPhone(userEntity.getPhone());
        mineUserInfo.setCreateTime(userEntity.getCreateTime());
        if(wechatUserEntity != null){
            mineUserInfo.setWechatId(wechatUserEntity.getWechatId());
        }


        mineUserInfo.setActiveScore(activeScoreCalculator.cal(
                ActiveScoreRequest.total(userId)
        ).getActiveScore().intValue());

        return mineUserInfo;
    }

    @Override
    public UserIdentify getUserIdentifyById(Long userId) {
        if(userId == null){
            throw BizRuntimeException.from(ResultCode.ERROR, "???????????????");
        }


        UserEntity userEntity = getById(userId);
        if(userEntity == null){
            throw BizRuntimeException.from(ResultCode.NOT_FOUND, "???????????????");
        }



        return UserUtils.getUserIdentify(userEntity.getIdentify());
    }
}
