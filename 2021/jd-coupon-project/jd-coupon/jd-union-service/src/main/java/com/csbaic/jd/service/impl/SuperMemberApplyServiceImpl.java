package com.csbaic.jd.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeData;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csbaic.common.convert.ObjectConvert;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.common.result.ResultCode;
import com.csbaic.jd.config.MiniAppConstants;
import com.csbaic.jd.dto.ApplySuperMember;
import com.csbaic.jd.dto.RejectSuperMemberApply;
import com.csbaic.jd.dto.SuperMemberApplyInfo;
import com.csbaic.jd.dto.SuperMemberApplyOrderInfo;
import com.csbaic.jd.entity.*;
import com.csbaic.jd.enums.SuperMemberApplyStatus;
import com.csbaic.jd.enums.UserIdentify;
import com.csbaic.jd.mapper.SuperMemberApplyMapper;
import com.csbaic.jd.service.*;
import com.csbaic.jd.service.group.GroupIdGenerator;
import com.csbaic.jd.utils.SplitterUtils;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 超级会员申请表 服务实现类
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-24
 */
@Service
public class SuperMemberApplyServiceImpl extends ServiceImpl<SuperMemberApplyMapper, SuperMemberApplyEntity> implements ISuperMemberApplyService {

    private static String GROUP_PREFIX = "尚橙京东内部特惠群";

    private final GroupIdGenerator groupIdGenerator;

    private final ITeamMemberService teamMemberService;

    private final IWechatUserService wechatUserService;

    private final IUserService userService;

    private final IWechatGroupService wechatGroupService;

    private final WxMaService wxMaService;



    public SuperMemberApplyServiceImpl(GroupIdGenerator groupIdGenerator, ITeamMemberService teamMemberService, IWechatUserService wechatUserService, IUserService userService, IWechatGroupService wechatGroupService, WxMaService wxMaService) {
        this.groupIdGenerator = groupIdGenerator;
        this.teamMemberService = teamMemberService;
        this.wechatUserService = wechatUserService;
        this.userService = userService;
        this.wechatGroupService = wechatGroupService;
        this.wxMaService = wxMaService;
    }

    @Override
    @Transactional
    public void approveSuperMemberApply(Long userId, Long applyId) {

        QueryWrapper<SuperMemberApplyEntity> wrappers = Wrappers.<SuperMemberApplyEntity>query()
                .eq(SuperMemberApplyEntity.ID, applyId);

        SuperMemberApplyEntity applyEntity = getOne(wrappers);
        if(applyEntity == null){
            throw   BizRuntimeException.from(ResultCode.ERROR, "没有找到申请单");
        }

        SuperMemberApplyStatus status = SuperMemberApplyStatus.statusOf(applyEntity.getStatus());
        if(status == null){
            throw   BizRuntimeException.from(ResultCode.ERROR, "申请单状态不正解");
        }

        switch (status){

            case WAIT_FOR_APPLY:
                throw   BizRuntimeException.from(ResultCode.ERROR, "还没有提交的申请单");
            case SUBMITTED:
                break;
            case APPROVED:
            case REJECTED:
                throw   BizRuntimeException.from(ResultCode.ERROR, "已经处理过了");
        }


        applyEntity.setRemark("申请通过");
        applyEntity.setStatus(SuperMemberApplyStatus.APPROVED.getStatus());
        updateById(applyEntity);

        WechatGroupEntity wechatGroupEntity = wechatGroupService.getOne(
                Wrappers.<WechatGroupEntity>query()
                .eq(WechatGroupEntity.OWNER_ID, applyEntity.getOwnerId())

        );

        if(wechatGroupEntity != null){
            throw   BizRuntimeException.from(ResultCode.ERROR, "已经是超级会员了，重新操作");
        }

        wechatGroupEntity = new WechatGroupEntity();
        wechatGroupEntity.setGroupId(applyEntity.getGroupId());
        wechatGroupEntity.setGroupName(GROUP_PREFIX + applyEntity.getGroupId());
        wechatGroupEntity.setOwnerId(applyEntity.getOwnerId());
        wechatGroupService.save(wechatGroupEntity);

        UserEntity userEntity = userService.getById(applyEntity.getOwnerId());
        if(userEntity == null){
            throw   BizRuntimeException.from(ResultCode.ERROR, "找不到用户？？");
        }

        userEntity.setIdentify(UserIdentify.SUPER.getCode());
        userService.updateById(userEntity);

        sendSuperMemberAppResultSubscriptionMsg(applyEntity.getOwnerId(), "管理员", "已通过", "恭喜您已成为导师！", MiniAppConstants.HOME_PAGE);
    }

    @Override
    public void rejectSuperMemberApply(Long userId, Long applyId, RejectSuperMemberApply info) {
        QueryWrapper<SuperMemberApplyEntity> wrappers = Wrappers.<SuperMemberApplyEntity>query()
                .eq(SuperMemberApplyEntity.ID, applyId);


        SuperMemberApplyEntity applyEntity = getOne(wrappers);
        if(applyEntity == null){
            throw   BizRuntimeException.from(ResultCode.ERROR, "没有找到申请单");
        }

        SuperMemberApplyStatus status = SuperMemberApplyStatus.statusOf(applyEntity.getStatus());
        switch (status){

            case WAIT_FOR_APPLY:
                throw   BizRuntimeException.from(ResultCode.ERROR, "还没有提交的申请单");
            case SUBMITTED:
                break;
            case APPROVED:
            case REJECTED:
                throw   BizRuntimeException.from(ResultCode.ERROR, "已经处理过了");
        }


        applyEntity.setStatus(SuperMemberApplyStatus.REJECTED.getStatus());
        applyEntity.setRemark(info.getRemark());
        saveOrUpdate(applyEntity);
        sendSuperMemberAppResultSubscriptionMsg(applyEntity.getOwnerId(), "管理员", "未通过", "很遗憾您未能通过导师审核！", MiniAppConstants.SUPER_MEMBER_APPLY);
    }

    @Override
    public IPage<SuperMemberApplyOrderInfo> getSuperMemberApplyOrderInfo(Long userId, Integer status, int pageIndex, int pageSize) {
        QueryWrapper<SuperMemberApplyEntity> wrappers = Wrappers.<SuperMemberApplyEntity>query();

        if(status != null){
            wrappers.eq(SuperMemberApplyEntity.STATUS, status);
        }


        return page(new Page<>(pageIndex, pageSize), wrappers)
                .convert(new Function<SuperMemberApplyEntity, SuperMemberApplyOrderInfo>() {
                    @Nullable
                    @Override
                    public SuperMemberApplyOrderInfo apply(@Nullable SuperMemberApplyEntity input) {
                        return toSuperMemberApplyOrderInfo(input);
                    }
                });
    }



    @Override
    public SuperMemberApplyInfo getSuperMemberApplyInfo(Long userId) {
        if(userId == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "用户Id不能为空");
        }

        SuperMemberApplyEntity superMemberApplyEntity = getOne(
                Wrappers.<SuperMemberApplyEntity>query()
                .eq(SuperMemberApplyEntity.OWNER_ID, userId)
        );

        if(superMemberApplyEntity == null){
            superMemberApplyEntity = new SuperMemberApplyEntity();
            superMemberApplyEntity.setOwnerId(userId);
            superMemberApplyEntity.setGroupId(groupIdGenerator.create(userId));
            superMemberApplyEntity.setStatus(SuperMemberApplyStatus.WAIT_FOR_APPLY.getStatus());
            save(superMemberApplyEntity);
        }

        SuperMemberApplyInfo superMemberApplyInfo = new SuperMemberApplyInfo();
        superMemberApplyInfo.setGroupName(GROUP_PREFIX + superMemberApplyEntity.getGroupId());
        superMemberApplyInfo.setGroupId(superMemberApplyEntity.getGroupId());
        superMemberApplyInfo.setStatus(superMemberApplyEntity.getStatus());
        superMemberApplyInfo.setStatusDesc(SuperMemberApplyStatus.statusOf(superMemberApplyEntity.getStatus()).getName());
        superMemberApplyInfo.setRemark(superMemberApplyEntity.getRemark());
        superMemberApplyInfo.setMemberCount(30);

        TeamMemberEntity teamMemberEntity = teamMemberService.findNearestLeaderOfMemberAndIdentify(userId, UserIdentify.SUPER.getCode());


        if (teamMemberEntity != null) {

            WechatUserEntity wechatUserEntity = wechatUserService.getOne(
                    Wrappers.<WechatUserEntity>query()
                            .eq(WechatUserEntity.USER_ID, teamMemberEntity.getLeaderId())
            );

            if (wechatUserEntity != null) {
                superMemberApplyInfo.setTeacherWechatId(wechatUserEntity.getWechatId());
            }
        }




        String images = superMemberApplyEntity.getImageUrls();
        if(!Strings.isNullOrEmpty(images)){
            List<String> imgs  = SplitterUtils.split(images, ";");
            superMemberApplyInfo.setImages(imgs);
        }


        SuperMemberApplyInfo.Example one = new SuperMemberApplyInfo.Example();
        one.setImageUrl("https://csbaic-jd-coupon.oss-cn-beijing.aliyuncs.com/super-member-apply/apply-case1.jpg");
        one.setStep("截图一（显示出群人数）：");

        SuperMemberApplyInfo.Example two = new SuperMemberApplyInfo.Example();
        two.setImageUrl("https://csbaic-jd-coupon.oss-cn-beijing.aliyuncs.com/super-member-apply/apply-case2.jpg");
        two.setStep("截图二（显示出群名称）：");

        List<SuperMemberApplyInfo.Example> examples = new ArrayList<>();
        examples.add(one);
        examples.add(two);
        superMemberApplyInfo.setExamples(examples);

        return superMemberApplyInfo;
    }

    @Override
    public void submitApplySuperMember(Long userId, ApplySuperMember applySuperMember) {

        if(userId == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "用户Id不能为空");
        }


        if(applySuperMember.getImageUrls() == null || applySuperMember.getImageUrls().isEmpty()){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "申请图片不能为空");
        }



        SuperMemberApplyEntity superMemberApplyEntity = getOne(
                Wrappers.<SuperMemberApplyEntity>query()
                        .eq(SuperMemberApplyEntity.OWNER_ID, userId)
        );


        if(superMemberApplyEntity == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "申请状态不正确，请稍后再试");
        }

        SuperMemberApplyStatus status = SuperMemberApplyStatus.statusOf(superMemberApplyEntity.getStatus());
        if(status == null){
            throw   BizRuntimeException.from(ResultCode.ERROR, "申请单状态不正确");
        }


        switch (status){
            //待提交状态、已提交、已拒绝可以再次提交
            case WAIT_FOR_APPLY:
            case SUBMITTED:
            case REJECTED:
                break;
            case APPROVED:
                throw   BizRuntimeException.from(ResultCode.ERROR, "申请单已经通过，请不要重新提交");

        }



        String imageUrls = SplitterUtils.stringfiy(applySuperMember.getImageUrls(),";");
        superMemberApplyEntity.setImageUrls(imageUrls);
        superMemberApplyEntity.setStatus(SuperMemberApplyStatus.SUBMITTED.getStatus());
        superMemberApplyEntity.setRemark("提交申请");
        saveOrUpdate(superMemberApplyEntity);
    }

    private SuperMemberApplyOrderInfo toSuperMemberApplyOrderInfo(SuperMemberApplyEntity superMemberApplyEntity){
        ObjectConvert<SuperMemberApplyEntity, SuperMemberApplyOrderInfo> convert = ObjectConvert.spring(SuperMemberApplyOrderInfo.class);
        SuperMemberApplyOrderInfo orderInfo = convert.convert(superMemberApplyEntity);

        if(!Strings.isNullOrEmpty(superMemberApplyEntity.getImageUrls())){
            List<String> images = SplitterUtils.split(superMemberApplyEntity.getImageUrls(), ";");
            orderInfo.setImageUrls(images);
        }

        UserEntity userEntity = userService.getById(superMemberApplyEntity.getOwnerId());
        orderInfo.setOwnerName(userEntity.getNickName());
        orderInfo.setStatusDesc(SuperMemberApplyStatus.statusOf(superMemberApplyEntity.getStatus()).getName());
        return orderInfo;
    }



    private void sendSuperMemberAppResultSubscriptionMsg(Long toUser, String obj, String result, String remark, String page ){

        WechatUserEntity wechatUserEntity = wechatUserService.getOne(
                Wrappers.<WechatUserEntity>query()
                        .eq(WechatUserEntity.USER_ID, toUser)
        );


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");

        //没有微信用户，不发送消息
        if(wechatUserEntity  == null){
            return;
        }

        List<WxMaSubscribeData> data = new ArrayList<>();
        data.add(new WxMaSubscribeData("thing1",  obj));
        data.add(new WxMaSubscribeData("phrase2", result));
        data.add(new WxMaSubscribeData("time3", formatter.format(LocalDateTime.now())));
        data.add(new WxMaSubscribeData("thing4", remark));



        WxMaSubscribeMessage subscribeMessage =  WxMaSubscribeMessage.builder()
                .toUser(wechatUserEntity.getOpenId())
                .templateId("o3j9VMu9_vm8UIDJ0lfqLSFZWxzuY2r8qI5qyUybyI8")
                .page(page == null ? "/" : page)
                .data(data)
                .build();

        try {
            wxMaService.getMsgService().sendSubscribeMsg(subscribeMessage);
        } catch (WxErrorException e) {
            log.error("", e);
        }
    }
}
