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
            throw BizRuntimeException.from(ResultCode.NOT_FOUND, "未找到用户：" + userId);
        }


        TeamMemberEntity teacherMember = teamMemberService.findNearestLeaderOfMemberAndIdentify(userId, UserIdentify.TEACHER.getCode());


        if (teacherMember != null) {
            SimpleMemberUserInfo  teacher = memberMapper.getMemberInfoOfUserId(teacherMember.getLeaderId());
            if(teacher == null){
                throw  BizRuntimeException.from(ResultCode.NOT_FOUND, "没有找到导师:" + teacherMember.getLeaderId());
            }

            return teacher;
        }


        return null;

    }

    @Override
    public SimpleMemberUserInfo getMemberInfoOfUser(Long userId) {
        if (userId == null) {
            throw BizRuntimeException.from(ResultCode.NOT_FOUND, "未找到用户：" + userId);
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
            throw BizRuntimeException.from(ResultCode.NOT_FOUND, "未找到用户：" + userId);
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

        //导师
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
     * 获取用户的活跃度
     * @param userId
     * @param userIdentify
     * @return
     */
    private GradeMemberInfo.MemberInfoBlock getActiveScoreBlock(Long userId, UserIdentify userIdentify){

        //超级会员以下不显示活跃度
        if(userIdentify.getCode() < UserIdentify.SUPER.getCode()){
            return null;
        }

        //活跃度
        GradeMemberInfo.MemberInfoBlock orderBlock = new GradeMemberInfo.MemberInfoBlock();
        orderBlock.setHideable(true);
        orderBlock.setTitle("活跃度");
        orderBlock.setHideable(true);

        List<GradeMemberInfo.MemberInfoBlockItem> items = new ArrayList<>();

        ActiveScore activeScoreForToday = activeScoreCalculator.cal(ActiveScoreRequest.today(userId));
        ActiveScore totalActiveScore = activeScoreCalculator.cal(ActiveScoreRequest.total(userId));



        items.add(new GradeMemberInfo.MemberInfoBlockItem(
           "今日活跃度", activeScoreForToday.getActiveScore().intValue() + ""
        ));


        items.add(new GradeMemberInfo.MemberInfoBlockItem(
                "总活跃度", totalActiveScore.getActiveScore().intValue() + ""
        ));
        orderBlock.setItems(items);

        return orderBlock;
    }


    private GradeMemberInfo.MemberInfoBlock getMemberOrderBlock(Long userId, UserIdentify userIdentify){
        //我的团队块
        GradeMemberInfo.MemberInfoBlock orderBlock = new GradeMemberInfo.MemberInfoBlock();
        orderBlock.setHideable(true);
        orderBlock.setTitle("我的订单");
        orderBlock.setMore("/page_package/order/order?identify="  + userIdentify.getCode());

        List<GradeMemberInfo.MemberInfoBlockItem> items = new ArrayList<>();
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime toDayEnd = LocalDateTime.of(todayStart.toLocalDate(), LocalTime.MAX);
        LocalDateTime yesterdayStart = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime yesterdayEnd = LocalDateTime.of(yesterdayStart.toLocalDate(), LocalTime.MAX);

        if(UserIdentify.REGISTERED == userIdentify){
            items.add(new GradeMemberInfo.MemberInfoBlockItem("今日购买订单量",
                    orderDetailService.getCountWithBuyerBetween(userId, todayStart, toDayEnd ) + "" ));

            items.add(new GradeMemberInfo.MemberInfoBlockItem("昨日购买订单量",
                    orderDetailService.getCountWithBuyerBetween(userId, yesterdayStart, yesterdayEnd ) + "" ));
        }else {
            items.add(new GradeMemberInfo.MemberInfoBlockItem("今日购买订单量",
                    orderDetailService.getCountWithBuyerBetween(userId, todayStart, toDayEnd ) + "" ));

            items.add(new GradeMemberInfo.MemberInfoBlockItem("昨日推广订单量",
                    orderDetailService.getCountWithOwnerBetween(userId, yesterdayStart, yesterdayEnd ) + "" ));
        }

        orderBlock.setItems(items);

        return orderBlock;
    }



    private GradeMemberInfo.MemberInfoBlock getMemberCountBlock(Long userId, UserIdentify userIdentify){
        //我的团队块
        GradeMemberInfo.MemberInfoBlock memberCountBlock = new GradeMemberInfo.MemberInfoBlock();
        memberCountBlock.setHideable(true);
        memberCountBlock.setTitle("我的团队");
        memberCountBlock.setMore("/page_package/fans/fans?identify=" + userIdentify.getCode());

        List<GradeMemberInfo.MemberInfoBlockItem> memberCountBlockItems = new ArrayList<>();

        if(UserIdentify.REGISTERED == userIdentify){
            memberCountBlockItems.add(new GradeMemberInfo.MemberInfoBlockItem("今日新增",
                    teamMemberService.countByLeaderAndCreateTimeBetween(userId, LocalDate.now().atStartOfDay(), LocalDate.now().plusDays(1).atStartOfDay() ) + "人"));

            memberCountBlockItems.add(new GradeMemberInfo.MemberInfoBlockItem("全部全员",
                    teamMemberService.countByLeader(userId) + "人"));
        }else {
            memberCountBlockItems.add(new GradeMemberInfo.MemberInfoBlockItem("我的注册用户",
                    teamMemberService.countByLeaderIdAndUserIdentifyAndLevel(userId, UserIdentify.REGISTERED.getCode(), 1) + "人"));

            memberCountBlockItems.add(new GradeMemberInfo.MemberInfoBlockItem("我的超级会员",
                    teamMemberService.countByLeaderIdAndUserIdentifyAndLevel(userId, UserIdentify.SUPER.getCode(), 1) + "人"));

            memberCountBlockItems.add(new GradeMemberInfo.MemberInfoBlockItem("全部超级会员",
                    teamMemberService.countByLeaderIdAndUserIdentifyAndLevel(userId, UserIdentify.SUPER.getCode(), null) + "人"));

            memberCountBlockItems.add(new GradeMemberInfo.MemberInfoBlockItem("全部成员",
                    teamMemberService.countByLeaderIdAndUserIdentifyAndLevel(userId, null, null) + "人"));
        }

        memberCountBlock.setItems(memberCountBlockItems);

        return memberCountBlock;
    }

    private GradeMemberInfo.MemberInfoBlock getMemberFeeBlock(Long userId, UserIdentify userIdentify){
        GradeMemberInfo.MemberInfoBlock memberFeeBlock = new GradeMemberInfo.MemberInfoBlock();
        memberFeeBlock.setHideable(true);
        memberFeeBlock.setTitle("预估收益");
        memberFeeBlock.setMore("/page_package/profit/profit?identify="  + userIdentify.getCode());

        List<GradeMemberInfo.MemberInfoBlockItem> items = new ArrayList<>();

        if(UserIdentify.REGISTERED == userIdentify){

            LocalDateTime todayStart = LocalDate.now().atStartOfDay();
            LocalDateTime toDayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
            LocalDateTime yesterdayStart = LocalDate.now().minusDays(1).atStartOfDay();
            LocalDateTime yesterdayEnd = LocalDateTime.of(yesterdayStart.toLocalDate(), LocalTime.MAX);

            items.add(new GradeMemberInfo.MemberInfoBlockItem("今日返利收益",
                  "¥" +  bigDecimalFormatter.format(memberCommissionService.getEstimateRebateFeeBetween(userId, todayStart, toDayEnd))
            ));

            items.add(new GradeMemberInfo.MemberInfoBlockItem("昨日返利收益",
                    "¥" +  bigDecimalFormatter.format(memberCommissionService.getEstimateRebateFeeBetween(userId, yesterdayStart, yesterdayEnd))
            ));

        }else {
            LocalDateTime todayStart = LocalDate.now().atStartOfDay();
            LocalDateTime toDayEnd = LocalDate.now().plusDays(1).atStartOfDay().minusSeconds(1);

            items.add(new GradeMemberInfo.MemberInfoBlockItem("今日推广收益",
                    "¥" +  bigDecimalFormatter.format(memberCommissionService.getEstimateCommissionFeeBetween(userId, todayStart, toDayEnd))
            ));


            items.add(new GradeMemberInfo.MemberInfoBlockItem("今日平台奖励收益",
                    "¥" +  bigDecimalFormatter.format(memberCommissionService.getEstimateAwardFeeBetween(userId, todayStart, toDayEnd))
            ));

            items.add(new GradeMemberInfo.MemberInfoBlockItem("今日返利收益",
                    "¥" +  bigDecimalFormatter.format(memberCommissionService.getEstimateRebateFeeBetween(userId, todayStart, toDayEnd))
            ));

            items.add(new GradeMemberInfo.MemberInfoBlockItem("今日全部收益",
                    "¥" +  bigDecimalFormatter.format(memberCommissionService.getTotalEstimateFeeBetween(userId, todayStart, toDayEnd))
            ));
        }

        memberFeeBlock.setItems(items);

        return memberFeeBlock;
    }

    @Override
    public IPage<SimpleMemberUserInfo> getMembersOf(Long userId,  QueryMember queryMember) {
        if(userId == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "用户id为空");
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
            throw  BizRuntimeException.from(ResultCode.NOT_FOUND, "没有找到用户:" +  userId);
        }

        List<Long> exclude = new ArrayList<>();
        exclude.add(userId);
        UserContacts userContacts = new UserContacts();
        SimpleMemberUserInfo mine = memberMapper.getMemberInfoOfUserId(userId);


        userContacts.setMine(mine);

        TeamMemberEntity teacherMember = teamMemberService.findNearestLeaderOfMemberAndIdentify(userId, UserIdentify.TEACHER.getCode());
        //直属导师
        if(teacherMember != null){
            SimpleMemberUserInfo  teacher = memberMapper.getMemberInfoOfUserId(teacherMember.getLeaderId());
            if(teacher == null){
                throw  BizRuntimeException.from(ResultCode.NOT_FOUND, "没有找到导师:" + teacherMember.getLeaderId());
            }
            exclude.add(teacherMember.getId());
            userContacts.setTeacher(teacher);
        }

        //邀请人
        SimpleMemberUserInfo inviter = memberMapper.getMemberInfoOfUserId(userEntity.getInviterId());

        /*
               系统初始化用户是没有邀请人的
         */
        if (inviter != null) {
            userContacts.setInviter(inviter);
            exclude.add(inviter.getUserId());
        }


        //粉丝
        IPage<SimpleMemberUserInfo> fans = memberMapper.getMembersInfoOfUserIdAndNotIn(new Page<>(pageIndex, pageSize), userId, exclude);
        userContacts.setFans(fans.getRecords());

        //后处理数据
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
     * 返回导师信息
     * @param userId
     * @return
     */
    private SimpleMemberUserInfo getTeacherInfo(Long userId){


        //找出直属导师
        TeamMemberEntity teamMemberEntity = teamMemberService.findNearestLeaderOfMemberAndIdentify(userId, UserIdentify.TEACHER.getCode());
        SimpleMemberUserInfo teacherInfo = null;
        //fixme 如果自己是导师是否要显示导师？
        if (teamMemberEntity != null) {
            UserEntity teacherUserEntity = userService.getById(teamMemberEntity.getLeaderId());

            //未找到导师
            if (teacherUserEntity == null){
                throw BizRuntimeException.from(ResultCode.NOT_FOUND, "获取导师信息失败：" + teamMemberEntity.getLeaderId());
            }

            teacherInfo = new SimpleMemberUserInfo();
            BeanUtils.copyProperties(teacherUserEntity, teacherInfo);
            teacherInfo.setUserId(teacherUserEntity.getId());

            WechatUserEntity wechatUserEntity = wechatUserService.getOne(
                    Wrappers.<WechatUserEntity>query()
                            .eq(WechatUserEntity.USER_ID, teacherUserEntity.getId())
            );

            //fixme 是否允许空微信？
            teacherInfo.setWechatId(wechatUserEntity != null ? wechatUserEntity.getWechatId() : "");

        }

        return teacherInfo;
    }


    @Override
    public MemberFeeSegments getMemberFeeBlocks(Long userId) {

        if(userId == null){
            throw BizRuntimeException.from(ResultCode.NOT_FOUND, "用户id不能为空");
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


        //预估收益
        MemberFeeBlock estimate  = new MemberFeeBlock();
        List<MemberFeeBlock.Item> items = new ArrayList<>();


        BigDecimal estimateTotalFee = memberCommissionService.getTotalEstimateFeeBetween(userId, firstDay, lastDay);
        BigDecimal estimateRebateFee = memberCommissionService.getEstimateRebateFeeBetween(userId, firstDay, lastDay);


        items.add(new MemberFeeBlock.Item("近30天预估总收益", bigDecimalFormatter.format( estimateTotalFee)));
        items.add(new MemberFeeBlock.Item("近30天预估返利收益", bigDecimalFormatter.format( estimateRebateFee)));

        if(UserIdentify.REGISTERED != identify){
            BigDecimal estimateCommissionFee = memberCommissionService.getEstimateCommissionFeeBetween(userId, firstDay, lastDay);
            BigDecimal estimateAwardFee = memberCommissionService.getEstimateAwardFeeBetween(userId, firstDay, lastDay);
            items.add(new MemberFeeBlock.Item("上月推广收益", bigDecimalFormatter.format( estimateCommissionFee)));
            items.add(new MemberFeeBlock.Item("上月平台奖励收益", bigDecimalFormatter.format( estimateAwardFee)));
        }


        estimate.setItems(items);
        estimate.setDescription(new MemberFeeBlock.DataDescription(
                MessageFormat.format("*统计包含{0,date,yyyy-MM-dd} - {1,date,yyyy-MM-dd} 的数据",
                        TimeUtils.toMillis(firstDay),
                        TimeUtils.toMillis(lastDay)),
                "预估收益说明",
                "关于预估收益说明",
                "关于预估收益说明"
        ));


        List<MemberFeeBlock> segments = new ArrayList<>();
        segments.add(estimate);
        return segments;
    }


    public List<MemberFeeBlock> getFeeForLastMonth(Long userId, UserIdentify identify){
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        LocalDateTime firstDayOfLastMonth = LocalDateTime.of(lastMonth.with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN) ;
        LocalDateTime  lastDayOfLastMonth =  LocalDateTime.of(lastMonth.with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX) ;

        //预估收益
        MemberFeeBlock estimate  = new MemberFeeBlock();
        List<MemberFeeBlock.Item> items = new ArrayList<>();

        BigDecimal estimateTotalFee = memberCommissionService.getTotalEstimateFeeBetween(userId, firstDayOfLastMonth, lastDayOfLastMonth);
        BigDecimal estimateRebateFee = memberCommissionService.getEstimateRebateFeeBetween(userId, firstDayOfLastMonth, lastDayOfLastMonth);

        items.add(new MemberFeeBlock.Item("上个月预估总收益", bigDecimalFormatter.format( estimateTotalFee)));
        items.add(new MemberFeeBlock.Item("上月预估返利收益", bigDecimalFormatter.format( estimateRebateFee)));

        if(UserIdentify.REGISTERED != identify){
            BigDecimal estimateCommissionFee = memberCommissionService.getEstimateCommissionFeeBetween(userId, firstDayOfLastMonth, lastDayOfLastMonth);
            BigDecimal estimateAwardFee = memberCommissionService.getEstimateAwardFeeBetween(userId, firstDayOfLastMonth, lastDayOfLastMonth);
            items.add(new MemberFeeBlock.Item("上月推广收益", bigDecimalFormatter.format( estimateCommissionFee)));
            items.add(new MemberFeeBlock.Item("上月平台奖励收益", bigDecimalFormatter.format( estimateAwardFee)));
        }

        estimate.setItems(items);

        estimate.setDescription(new MemberFeeBlock.DataDescription(
                MessageFormat.format("*统计包含{0,date,yyyy-MM-dd} - {1,date,yyyy-MM-dd} 的数据",
                        TimeUtils.toMillis(lastMonth.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay()),
                        TimeUtils.toMillis(lastMonth.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay())),
                "预估收益说明",
                "关于预估收益说明",
                "根据东京推广规则：预估收益是根据用户下单时间预估统计的推广效果数据，并非实际结算收益，订单取消或者退换货后对应收益会相应扣除，实际收益以订单完成结算为准。"
        ));


        List<MemberFeeBlock> segments = new ArrayList<>();
        segments.add(estimate);
        return segments;
    }


    public List<MemberFeeBlock> getFeeForThisMonth(Long userId, UserIdentify identify){
        LocalDate thisMonth = LocalDate.now();
        LocalDateTime firstDay = LocalDateTime.of(thisMonth.with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN) ;
        LocalDateTime  lastDay =  LocalDateTime.of(thisMonth.with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX) ;



        //预估收益
        MemberFeeBlock estimate  = new MemberFeeBlock();
        List<MemberFeeBlock.Item> items = new ArrayList<>();

        BigDecimal estimateTotalFee = memberCommissionService.getTotalEstimateFeeBetween(userId, firstDay, lastDay);
        BigDecimal estimateRebateFee = memberCommissionService.getEstimateRebateFeeBetween(userId, firstDay, lastDay);


        items.add(new MemberFeeBlock.Item("本月预估总收益", bigDecimalFormatter.format( estimateTotalFee)));
        items.add(new MemberFeeBlock.Item("本月预估返利收益", bigDecimalFormatter.format(estimateRebateFee)));

        if(UserIdentify.REGISTERED != identify){
            BigDecimal estimateCommissionFee = memberCommissionService.getEstimateCommissionFeeBetween(userId, firstDay, lastDay);
            BigDecimal estimateAwardFee = memberCommissionService.getEstimateAwardFeeBetween(userId, firstDay, lastDay);
            items.add(new MemberFeeBlock.Item("本月推广收益", bigDecimalFormatter.format( estimateCommissionFee)));
            items.add(new MemberFeeBlock.Item("本月平台奖励收益", bigDecimalFormatter.format( estimateAwardFee)));
        }

        estimate.setItems(items);
        estimate.setDescription(new MemberFeeBlock.DataDescription(
                MessageFormat.format("*统计包含{0,date,yyyy-MM-dd} - {1,date,yyyy-MM-dd} 的数据",
                        TimeUtils.toMillis(thisMonth.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay()),
                        TimeUtils.toMillis(thisMonth.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay())),
                "预估收益说明",
                "关于预估收益说明",
                "根据东京推广规则：预估收益是根据用户下单时间预估统计的推广效果数据，并非实际结算收益，订单取消或者退换货后对应收益会相应扣除，实际收益以订单完成结算为准。"
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


        //本月结算收益（上个月）
        MemberBilling lastMonthBilling =  memberCommissionService.getEstimateSettlementFee(userId,firstDayOfLastMonth, lastDayOfLastMonth );

        billingItems.add(new MemberFeeBlock.Item("本月预估结算金额", bigDecimalFormatter.format( lastMonthBilling.getActualRebateFee().add(lastMonthBilling.getActualAwardFee()).add(lastMonthBilling.getActualCommissionFee()))));

        LocalDate thisMonth = LocalDate.now();
        LocalDateTime firstDayOfThisMonth = LocalDateTime.of(thisMonth.with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN) ;
        LocalDateTime  lastDayOfThisMonth =  LocalDateTime.of(thisMonth.with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX) ;

        //下月结算收益（本月）
        MemberBilling nextMonthBilling = memberCommissionService.getEstimateSettlementFee(userId, firstDayOfThisMonth, lastDayOfThisMonth);
        billingItems.add(new MemberFeeBlock.Item("下月预估结算金额", bigDecimalFormatter.format(nextMonthBilling.getActualRebateFee().add(nextMonthBilling.getActualAwardFee()).add(nextMonthBilling.getActualCommissionFee()))));
        billingSegment.setItems(billingItems);
        billingSegment.setDescription(new MemberFeeBlock.DataDescription(
                "",
                "预估结算收益说明",
                "预估结算收益说明",
                "根据东京推广规则：预估结算收益是根据用户订单完成时间统计，本月结算【订单完成时间在上月的订单收益】，每个月27~30日结算提现。订单完成时间不在上月的会在下个月进行结算。"
        ));

        return billingSegment;
    }

    private  void postProcessSimpleMemberUserInfo(SimpleMemberUserInfo userInfo){
        if(userInfo == null){
            return;
        }

        userInfo.setIdentifyDesc(UserUtils.getUserIdentify(userInfo.getIdentify()).getName());
        //计算30天内的活跃度
        ActiveScore activeScore = activeScoreCalculator.cal(ActiveScoreRequest.total(userInfo.getUserId()));
        if(activeScore != null){
            userInfo.setActiveScore(activeScore.getActiveScore().intValue());
        }
    }
}
