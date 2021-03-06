package com.csbaic.jd.service.user.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.common.result.ResultCode;
import com.csbaic.jd.dto.*;
import com.csbaic.jd.entity.TeamMemberEntity;
import com.csbaic.jd.entity.UserEntity;
import com.csbaic.jd.entity.WechatUserEntity;
import com.csbaic.jd.enums.UserIdentify;
import com.csbaic.jd.mapper.MemberMapper;
import com.csbaic.jd.service.*;
import com.csbaic.jd.service.settlement.BigDecimalFormatter;
import com.csbaic.jd.service.settlement.MemberBilling;
import com.csbaic.jd.service.user.MemberService;
import com.csbaic.jd.service.user.active.ActiveScore;
import com.csbaic.jd.service.user.active.ActiveScoreCalculator;
import com.csbaic.jd.service.user.active.ActiveScoreRequest;
import com.csbaic.jd.utils.TimeUtils;
import com.csbaic.jd.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private IUserService userService;

    @Autowired
    private ITeamMemberService teamMemberService;

    @Autowired
    private IOrderDetailService orderDetailService;

    @Autowired
    private ActiveScoreCalculator activeScoreCalculator;

    @Autowired
    private IWechatUserService wechatUserService;

    @Autowired
    private IMemberCommissionService memberCommissionService;


    @Autowired
    private BigDecimalFormatter bigDecimalFormatter;

    @Override
    public SimpleMemberUserInfo getFirstTeacherOf(Long userId) {
        if (userId == null) {
            throw BizRuntimeException.from(ResultCode.NOT_FOUND, "??????????????????" + userId);
        }


        TeamMemberEntity teacherMember = teamMemberService.findNearestLeaderOfMemberAndIdentify(userId, UserIdentify.TEACHER.getCode());


        if (teacherMember != null) {
            SimpleMemberUserInfo  teacher = memberMapper.getMemberInfoOfUserId(teacherMember.getLeaderId());
            if(teacher == null){
                throw  BizRuntimeException.from(ResultCode.NOT_FOUND, "??????????????????:" + teacherMember.getLeaderId());
            }

            return teacher;
        }


        return null;

    }

    @Override
    public SimpleMemberUserInfo getMemberInfoOfUser(Long userId) {
        if (userId == null) {
            throw BizRuntimeException.from(ResultCode.NOT_FOUND, "??????????????????" + userId);
        }

        return memberMapper.getMemberInfoOfUserId(userId);
    }

    @Override
    public GradeMemberInfo getGradeMemberInfo(Long userId) {

        if (userId == null) {
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST);
        }

        UserEntity userEntity = userService.getById(userId);
        if (userEntity == null) {
            throw BizRuntimeException.from(ResultCode.NOT_FOUND, "??????????????????" + userId);
        }

        WechatUserEntity wechatUserEntity = wechatUserService.getOne(
                Wrappers.<WechatUserEntity>query()
                .eq(WechatUserEntity.USER_ID, userEntity.getId())

        );

        GradeMemberInfo memberInfo = new GradeMemberInfo();

        SimpleMemberUserInfo memberUserInfo = new SimpleMemberUserInfo();
        BeanUtils.copyProperties(userEntity, memberUserInfo);
        memberUserInfo.setUserId(userEntity.getId());
        UserIdentify identify = UserUtils.getUserIdentify(userEntity.getIdentify());
        memberUserInfo.setIdentifyDesc(identify.getName());
        memberUserInfo.setWechatId( wechatUserEntity != null ? wechatUserEntity.getWechatId() : null);
        postProcessSimpleMemberUserInfo(memberUserInfo);
        memberInfo.setUserInfo(memberUserInfo);

        //??????
        SimpleMemberUserInfo teacherInfo = getTeacherInfo(userId);
        postProcessSimpleMemberUserInfo(teacherInfo);
        memberInfo.setTeacherInfo(teacherInfo);


        List<GradeMemberInfo.MemberInfoBlock> blocks = new ArrayList<>();

        GradeMemberInfo.MemberInfoBlock activeScoreBlock = getActiveScoreBlock(userId, identify);
        if(activeScoreBlock != null){
            blocks.add(getActiveScoreBlock(userId, identify));
        }

        blocks.add(getMemberCountBlock(userId, identify));
        blocks.add(getMemberFeeBlock(userId, identify));
        blocks.add(getMemberOrderBlock(userId, identify));
        memberInfo.setBlocks(blocks);


        return memberInfo;
    }

    /**
     * ????????????????????????
     * @param userId
     * @param userIdentify
     * @return
     */
    private GradeMemberInfo.MemberInfoBlock getActiveScoreBlock(Long userId, UserIdentify userIdentify){

        //????????????????????????????????????
        if(userIdentify.getCode() < UserIdentify.SUPER.getCode()){
            return null;
        }

        //?????????
        GradeMemberInfo.MemberInfoBlock orderBlock = new GradeMemberInfo.MemberInfoBlock();
        orderBlock.setHideable(true);
        orderBlock.setTitle("?????????");
        orderBlock.setHideable(true);

        List<GradeMemberInfo.MemberInfoBlockItem> items = new ArrayList<>();

        ActiveScore activeScoreForToday = activeScoreCalculator.cal(ActiveScoreRequest.today(userId));
        ActiveScore totalActiveScore = activeScoreCalculator.cal(ActiveScoreRequest.total(userId));



        items.add(new GradeMemberInfo.MemberInfoBlockItem(
           "???????????????", activeScoreForToday.getActiveScore().intValue() + ""
        ));


        items.add(new GradeMemberInfo.MemberInfoBlockItem(
                "????????????", totalActiveScore.getActiveScore().intValue() + ""
        ));
        orderBlock.setItems(items);

        return orderBlock;
    }


    private GradeMemberInfo.MemberInfoBlock getMemberOrderBlock(Long userId, UserIdentify userIdentify){
        //???????????????
        GradeMemberInfo.MemberInfoBlock orderBlock = new GradeMemberInfo.MemberInfoBlock();
        orderBlock.setHideable(true);
        orderBlock.setTitle("????????????");
        orderBlock.setMore("/page_package/order/order?identify="  + userIdentify.getCode());

        List<GradeMemberInfo.MemberInfoBlockItem> items = new ArrayList<>();
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime toDayEnd = LocalDateTime.of(todayStart.toLocalDate(), LocalTime.MAX);
        LocalDateTime yesterdayStart = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime yesterdayEnd = LocalDateTime.of(yesterdayStart.toLocalDate(), LocalTime.MAX);

        if(UserIdentify.REGISTERED == userIdentify){
            items.add(new GradeMemberInfo.MemberInfoBlockItem("?????????????????????",
                    orderDetailService.getCountWithBuyerBetween(userId, todayStart, toDayEnd ) + "" ));

            items.add(new GradeMemberInfo.MemberInfoBlockItem("?????????????????????",
                    orderDetailService.getCountWithBuyerBetween(userId, yesterdayStart, yesterdayEnd ) + "" ));
        }else {
            items.add(new GradeMemberInfo.MemberInfoBlockItem("?????????????????????",
                    orderDetailService.getCountWithBuyerBetween(userId, todayStart, toDayEnd ) + "" ));

            items.add(new GradeMemberInfo.MemberInfoBlockItem("?????????????????????",
                    orderDetailService.getCountWithOwnerBetween(userId, yesterdayStart, yesterdayEnd ) + "" ));
        }

        orderBlock.setItems(items);

        return orderBlock;
    }



    private GradeMemberInfo.MemberInfoBlock getMemberCountBlock(Long userId, UserIdentify userIdentify){
        //???????????????
        GradeMemberInfo.MemberInfoBlock memberCountBlock = new GradeMemberInfo.MemberInfoBlock();
        memberCountBlock.setHideable(true);
        memberCountBlock.setTitle("????????????");
        memberCountBlock.setMore("/page_package/fans/fans?identify=" + userIdentify.getCode());

        List<GradeMemberInfo.MemberInfoBlockItem> memberCountBlockItems = new ArrayList<>();

        if(UserIdentify.REGISTERED == userIdentify){
            memberCountBlockItems.add(new GradeMemberInfo.MemberInfoBlockItem("????????????",
                    teamMemberService.countByLeaderAndCreateTimeBetween(userId, LocalDate.now().atStartOfDay(), LocalDate.now().plusDays(1).atStartOfDay() ) + "???"));

            memberCountBlockItems.add(new GradeMemberInfo.MemberInfoBlockItem("????????????",
                    teamMemberService.countByLeader(userId) + "???"));
        }else {
            memberCountBlockItems.add(new GradeMemberInfo.MemberInfoBlockItem("??????????????????",
                    teamMemberService.countByLeaderIdAndUserIdentifyAndLevel(userId, UserIdentify.REGISTERED.getCode(), 1) + "???"));

            memberCountBlockItems.add(new GradeMemberInfo.MemberInfoBlockItem("??????????????????",
                    teamMemberService.countByLeaderIdAndUserIdentifyAndLevel(userId, UserIdentify.SUPER.getCode(), 1) + "???"));

            memberCountBlockItems.add(new GradeMemberInfo.MemberInfoBlockItem("??????????????????",
                    teamMemberService.countByLeaderIdAndUserIdentifyAndLevel(userId, UserIdentify.SUPER.getCode(), null) + "???"));

            memberCountBlockItems.add(new GradeMemberInfo.MemberInfoBlockItem("????????????",
                    teamMemberService.countByLeaderIdAndUserIdentifyAndLevel(userId, null, null) + "???"));
        }

        memberCountBlock.setItems(memberCountBlockItems);

        return memberCountBlock;
    }

    private GradeMemberInfo.MemberInfoBlock getMemberFeeBlock(Long userId, UserIdentify userIdentify){
        GradeMemberInfo.MemberInfoBlock memberFeeBlock = new GradeMemberInfo.MemberInfoBlock();
        memberFeeBlock.setHideable(true);
        memberFeeBlock.setTitle("????????????");
        memberFeeBlock.setMore("/page_package/profit/profit?identify="  + userIdentify.getCode());

        List<GradeMemberInfo.MemberInfoBlockItem> items = new ArrayList<>();

        if(UserIdentify.REGISTERED == userIdentify){

            LocalDateTime todayStart = LocalDate.now().atStartOfDay();
            LocalDateTime toDayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
            LocalDateTime yesterdayStart = LocalDate.now().minusDays(1).atStartOfDay();
            LocalDateTime yesterdayEnd = LocalDateTime.of(yesterdayStart.toLocalDate(), LocalTime.MAX);

            items.add(new GradeMemberInfo.MemberInfoBlockItem("??????????????????",
                  "??" +  bigDecimalFormatter.format(memberCommissionService.getEstimateRebateFeeBetween(userId, todayStart, toDayEnd))
            ));

            items.add(new GradeMemberInfo.MemberInfoBlockItem("??????????????????",
                    "??" +  bigDecimalFormatter.format(memberCommissionService.getEstimateRebateFeeBetween(userId, yesterdayStart, yesterdayEnd))
            ));

        }else {
            LocalDateTime todayStart = LocalDate.now().atStartOfDay();
            LocalDateTime toDayEnd = LocalDate.now().plusDays(1).atStartOfDay().minusSeconds(1);

            items.add(new GradeMemberInfo.MemberInfoBlockItem("??????????????????",
                    "??" +  bigDecimalFormatter.format(memberCommissionService.getEstimateCommissionFeeBetween(userId, todayStart, toDayEnd))
            ));


            items.add(new GradeMemberInfo.MemberInfoBlockItem("????????????????????????",
                    "??" +  bigDecimalFormatter.format(memberCommissionService.getEstimateAwardFeeBetween(userId, todayStart, toDayEnd))
            ));

            items.add(new GradeMemberInfo.MemberInfoBlockItem("??????????????????",
                    "??" +  bigDecimalFormatter.format(memberCommissionService.getEstimateRebateFeeBetween(userId, todayStart, toDayEnd))
            ));

            items.add(new GradeMemberInfo.MemberInfoBlockItem("??????????????????",
                    "??" +  bigDecimalFormatter.format(memberCommissionService.getTotalEstimateFeeBetween(userId, todayStart, toDayEnd))
            ));
        }

        memberFeeBlock.setItems(items);

        return memberFeeBlock;
    }

    @Override
    public IPage<SimpleMemberUserInfo> getMembersOf(Long userId,  QueryMember queryMember) {
        if(userId == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "??????id??????");
        }

        Integer identify = queryMember.getIdentify();
        int pageIndex = queryMember.getPageIndex() != null ? queryMember.getPageIndex() : 1;
        int pageSize = queryMember.getPageSize() != null ? queryMember.getPageSize() : 20;

        IPage<SimpleMemberUserInfo> page =  memberMapper.getMembersOfUserIdAndIdentify(new Page<>(pageIndex, pageSize), userId, identify);
        page.getRecords().forEach(this::postProcessSimpleMemberUserInfo);
        return page;
    }



    @Override
    public UserContacts getUserContacts(Long userId, QueryContacts contact) {
        if (userId == null) {
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST);
        }

        int pageIndex = contact.getPageIndex() != null ? contact.getPageIndex() : 1;
        int pageSize = contact.getPageSize() != null ? contact.getPageSize() : 20;

        UserEntity userEntity = userService.getById(userId);
        if(userEntity == null){
            throw  BizRuntimeException.from(ResultCode.NOT_FOUND, "??????????????????:" +  userId);
        }

        List<Long> exclude = new ArrayList<>();
        exclude.add(userId);
        UserContacts userContacts = new UserContacts();
        SimpleMemberUserInfo mine = memberMapper.getMemberInfoOfUserId(userId);


        userContacts.setMine(mine);

        TeamMemberEntity teacherMember = teamMemberService.findNearestLeaderOfMemberAndIdentify(userId, UserIdentify.TEACHER.getCode());
        //????????????
        if(teacherMember != null){
            SimpleMemberUserInfo  teacher = memberMapper.getMemberInfoOfUserId(teacherMember.getLeaderId());
            if(teacher == null){
                throw  BizRuntimeException.from(ResultCode.NOT_FOUND, "??????????????????:" + teacherMember.getLeaderId());
            }
            exclude.add(teacherMember.getId());
            userContacts.setTeacher(teacher);
        }

        //?????????
        SimpleMemberUserInfo inviter = memberMapper.getMemberInfoOfUserId(userEntity.getInviterId());

        /*
               ??????????????????????????????????????????
         */
        if (inviter != null) {
            userContacts.setInviter(inviter);
            exclude.add(inviter.getUserId());
        }


        //??????
        IPage<SimpleMemberUserInfo> fans = memberMapper.getMembersInfoOfUserIdAndNotIn(new Page<>(pageIndex, pageSize), userId, exclude);
        userContacts.setFans(fans.getRecords());

        //???????????????
        if(userContacts.getMine() != null){
            postProcessSimpleMemberUserInfo(userContacts.getMine());
        }
        if(userContacts.getTeacher() != null){
            postProcessSimpleMemberUserInfo(userContacts.getTeacher());
        }
        if(userContacts.getInviter() != null){
            postProcessSimpleMemberUserInfo(userContacts.getInviter());
        }

        if(userContacts.getFans() != null){
            userContacts.getFans().forEach(this::postProcessSimpleMemberUserInfo);
        }

        return userContacts;
    }





    /**
     * ??????????????????
     * @param userId
     * @return
     */
    private SimpleMemberUserInfo getTeacherInfo(Long userId){


        //??????????????????
        TeamMemberEntity teamMemberEntity = teamMemberService.findNearestLeaderOfMemberAndIdentify(userId, UserIdentify.TEACHER.getCode());
        SimpleMemberUserInfo teacherInfo = null;
        //fixme ?????????????????????????????????????????????
        if (teamMemberEntity != null) {
            UserEntity teacherUserEntity = userService.getById(teamMemberEntity.getLeaderId());

            //???????????????
            if (teacherUserEntity == null){
                throw BizRuntimeException.from(ResultCode.NOT_FOUND, "???????????????????????????" + teamMemberEntity.getLeaderId());
            }

            teacherInfo = new SimpleMemberUserInfo();
            BeanUtils.copyProperties(teacherUserEntity, teacherInfo);
            teacherInfo.setUserId(teacherUserEntity.getId());

            WechatUserEntity wechatUserEntity = wechatUserService.getOne(
                    Wrappers.<WechatUserEntity>query()
                            .eq(WechatUserEntity.USER_ID, teacherUserEntity.getId())
            );

            //fixme ????????????????????????
            teacherInfo.setWechatId(wechatUserEntity != null ? wechatUserEntity.getWechatId() : "");

        }

        return teacherInfo;
    }


    @Override
    public MemberFeeSegments getMemberFeeBlocks(Long userId) {

        if(userId == null){
            throw BizRuntimeException.from(ResultCode.NOT_FOUND, "??????id????????????");
        }

        UserEntity userEntity = userService.getById(userId);
        UserIdentify userIdentify = UserUtils.getUserIdentify(userEntity.getIdentify());
        MemberFeeSegments feeSegments = new MemberFeeSegments();
        feeSegments.setThisMonth( getFeeForThisMonth(userId, userIdentify) );
        feeSegments.setLastMonth( getFeeForLastMonth(userId,userIdentify) );
        feeSegments.setNearly30Days( getFeeForNearly30DayMonth(userId, userIdentify) );
        feeSegments.setBillingInfo(getBillingFeeSegment(userId));

        return feeSegments;
    }


    public List<MemberFeeBlock> getFeeForNearly30DayMonth(Long userId , UserIdentify identify){

        LocalDateTime firstDay= LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.MIN );
        LocalDateTime lastDay = LocalDateTime.of(LocalDate.now(),  LocalTime.MAX);


        //????????????
        MemberFeeBlock estimate  = new MemberFeeBlock();
        List<MemberFeeBlock.Item> items = new ArrayList<>();


        BigDecimal estimateTotalFee = memberCommissionService.getTotalEstimateFeeBetween(userId, firstDay, lastDay);
        BigDecimal estimateRebateFee = memberCommissionService.getEstimateRebateFeeBetween(userId, firstDay, lastDay);


        items.add(new MemberFeeBlock.Item("???30??????????????????", bigDecimalFormatter.format( estimateTotalFee)));
        items.add(new MemberFeeBlock.Item("???30?????????????????????", bigDecimalFormatter.format( estimateRebateFee)));

        if(UserIdentify.REGISTERED != identify){
            BigDecimal estimateCommissionFee = memberCommissionService.getEstimateCommissionFeeBetween(userId, firstDay, lastDay);
            BigDecimal estimateAwardFee = memberCommissionService.getEstimateAwardFeeBetween(userId, firstDay, lastDay);
            items.add(new MemberFeeBlock.Item("??????????????????", bigDecimalFormatter.format( estimateCommissionFee)));
            items.add(new MemberFeeBlock.Item("????????????????????????", bigDecimalFormatter.format( estimateAwardFee)));
        }


        estimate.setItems(items);
        estimate.setDescription(new MemberFeeBlock.DataDescription(
                MessageFormat.format("*????????????{0,date,yyyy-MM-dd} - {1,date,yyyy-MM-dd} ?????????",
                        TimeUtils.toMillis(firstDay),
                        TimeUtils.toMillis(lastDay)),
                "??????????????????",
                "????????????????????????",
                "????????????????????????"
        ));


        List<MemberFeeBlock> segments = new ArrayList<>();
        segments.add(estimate);
        return segments;
    }


    public List<MemberFeeBlock> getFeeForLastMonth(Long userId, UserIdentify identify){
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        LocalDateTime firstDayOfLastMonth = LocalDateTime.of(lastMonth.with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN) ;
        LocalDateTime  lastDayOfLastMonth =  LocalDateTime.of(lastMonth.with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX) ;

        //????????????
        MemberFeeBlock estimate  = new MemberFeeBlock();
        List<MemberFeeBlock.Item> items = new ArrayList<>();

        BigDecimal estimateTotalFee = memberCommissionService.getTotalEstimateFeeBetween(userId, firstDayOfLastMonth, lastDayOfLastMonth);
        BigDecimal estimateRebateFee = memberCommissionService.getEstimateRebateFeeBetween(userId, firstDayOfLastMonth, lastDayOfLastMonth);

        items.add(new MemberFeeBlock.Item("????????????????????????", bigDecimalFormatter.format( estimateTotalFee)));
        items.add(new MemberFeeBlock.Item("????????????????????????", bigDecimalFormatter.format( estimateRebateFee)));

        if(UserIdentify.REGISTERED != identify){
            BigDecimal estimateCommissionFee = memberCommissionService.getEstimateCommissionFeeBetween(userId, firstDayOfLastMonth, lastDayOfLastMonth);
            BigDecimal estimateAwardFee = memberCommissionService.getEstimateAwardFeeBetween(userId, firstDayOfLastMonth, lastDayOfLastMonth);
            items.add(new MemberFeeBlock.Item("??????????????????", bigDecimalFormatter.format( estimateCommissionFee)));
            items.add(new MemberFeeBlock.Item("????????????????????????", bigDecimalFormatter.format( estimateAwardFee)));
        }

        estimate.setItems(items);

        estimate.setDescription(new MemberFeeBlock.DataDescription(
                MessageFormat.format("*????????????{0,date,yyyy-MM-dd} - {1,date,yyyy-MM-dd} ?????????",
                        TimeUtils.toMillis(lastMonth.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay()),
                        TimeUtils.toMillis(lastMonth.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay())),
                "??????????????????",
                "????????????????????????",
                "???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????"
        ));


        List<MemberFeeBlock> segments = new ArrayList<>();
        segments.add(estimate);
        return segments;
    }


    public List<MemberFeeBlock> getFeeForThisMonth(Long userId, UserIdentify identify){
        LocalDate thisMonth = LocalDate.now();
        LocalDateTime firstDay = LocalDateTime.of(thisMonth.with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN) ;
        LocalDateTime  lastDay =  LocalDateTime.of(thisMonth.with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX) ;



        //????????????
        MemberFeeBlock estimate  = new MemberFeeBlock();
        List<MemberFeeBlock.Item> items = new ArrayList<>();

        BigDecimal estimateTotalFee = memberCommissionService.getTotalEstimateFeeBetween(userId, firstDay, lastDay);
        BigDecimal estimateRebateFee = memberCommissionService.getEstimateRebateFeeBetween(userId, firstDay, lastDay);


        items.add(new MemberFeeBlock.Item("?????????????????????", bigDecimalFormatter.format( estimateTotalFee)));
        items.add(new MemberFeeBlock.Item("????????????????????????", bigDecimalFormatter.format(estimateRebateFee)));

        if(UserIdentify.REGISTERED != identify){
            BigDecimal estimateCommissionFee = memberCommissionService.getEstimateCommissionFeeBetween(userId, firstDay, lastDay);
            BigDecimal estimateAwardFee = memberCommissionService.getEstimateAwardFeeBetween(userId, firstDay, lastDay);
            items.add(new MemberFeeBlock.Item("??????????????????", bigDecimalFormatter.format( estimateCommissionFee)));
            items.add(new MemberFeeBlock.Item("????????????????????????", bigDecimalFormatter.format( estimateAwardFee)));
        }

        estimate.setItems(items);
        estimate.setDescription(new MemberFeeBlock.DataDescription(
                MessageFormat.format("*????????????{0,date,yyyy-MM-dd} - {1,date,yyyy-MM-dd} ?????????",
                        TimeUtils.toMillis(thisMonth.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay()),
                        TimeUtils.toMillis(thisMonth.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay())),
                "??????????????????",
                "????????????????????????",
                "???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????"
        ));


        List<MemberFeeBlock> segments = new ArrayList<>();
        segments.add(estimate);
        return segments;
    }

    private MemberFeeBlock getBillingFeeSegment(Long userId){
        MemberFeeBlock billingSegment  = new MemberFeeBlock();
        List<MemberFeeBlock.Item> billingItems = new ArrayList<>();


        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        LocalDateTime firstDayOfLastMonth = LocalDateTime.of(lastMonth.with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN) ;
        LocalDateTime  lastDayOfLastMonth =  LocalDateTime.of(lastMonth.with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX) ;


        //?????????????????????????????????
        MemberBilling lastMonthBilling =  memberCommissionService.getEstimateSettlementFee(userId,firstDayOfLastMonth, lastDayOfLastMonth );

        billingItems.add(new MemberFeeBlock.Item("????????????????????????", bigDecimalFormatter.format( lastMonthBilling.getActualRebateFee().add(lastMonthBilling.getActualAwardFee()).add(lastMonthBilling.getActualCommissionFee()))));

        LocalDate thisMonth = LocalDate.now();
        LocalDateTime firstDayOfThisMonth = LocalDateTime.of(thisMonth.with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN) ;
        LocalDateTime  lastDayOfThisMonth =  LocalDateTime.of(thisMonth.with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX) ;

        //??????????????????????????????
        MemberBilling nextMonthBilling = memberCommissionService.getEstimateSettlementFee(userId, firstDayOfThisMonth, lastDayOfThisMonth);
        billingItems.add(new MemberFeeBlock.Item("????????????????????????", bigDecimalFormatter.format(nextMonthBilling.getActualRebateFee().add(nextMonthBilling.getActualAwardFee()).add(nextMonthBilling.getActualCommissionFee()))));
        billingSegment.setItems(billingItems);
        billingSegment.setDescription(new MemberFeeBlock.DataDescription(
                "",
                "????????????????????????",
                "????????????????????????",
                "???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????27~30?????????????????????????????????????????????????????????????????????????????????"
        ));

        return billingSegment;
    }

    private  void postProcessSimpleMemberUserInfo(SimpleMemberUserInfo userInfo){
        if(userInfo == null){
            return;
        }

        userInfo.setIdentifyDesc(UserUtils.getUserIdentify(userInfo.getIdentify()).getName());
        //??????30??????????????????
        ActiveScore activeScore = activeScoreCalculator.cal(ActiveScoreRequest.total(userInfo.getUserId()));
        if(activeScore != null){
            userInfo.setActiveScore(activeScore.getActiveScore().intValue());
        }
    }
}
