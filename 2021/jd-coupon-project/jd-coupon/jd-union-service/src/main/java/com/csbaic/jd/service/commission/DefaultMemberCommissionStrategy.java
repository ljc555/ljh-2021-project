package com.csbaic.jd.service.commission;

import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.common.result.ResultCode;
import com.csbaic.jd.enums.FeeType;
import com.csbaic.jd.enums.UserIdentify;
import com.csbaic.jd.service.order.SubUnionId;
import com.csbaic.jd.service.order.SubUnionIdConverter;
import com.csbaic.jd.service.order.handle.OrderMetadata;
import com.csbaic.jd.service.team.MemberContainer;
import com.csbaic.jd.service.team.MemberContainerBuilder;
import com.csbaic.jd.service.team.MemberInfo;
import com.csbaic.jd.service.team.bean.MemberNode;
import com.csbaic.jd.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by yjwfn on 2020/2/16.
 */
@Component
public class DefaultMemberCommissionStrategy implements CommissionStrategy {

    private final SubUnionIdConverter subUnionIdConverter;

    private final MemberContainerBuilder memberContainerBuilder;

    @Autowired
    public DefaultMemberCommissionStrategy(SubUnionIdConverter subUnionIdConverter, MemberContainerBuilder memberContainerBuilder) {
        this.subUnionIdConverter = subUnionIdConverter;
        this.memberContainerBuilder = memberContainerBuilder;
    }

    @Override
    public MemberContainer cal(OrderMetadata orderMetadata, OrderMetadata.SkuMetadata skuMetadata) {
        SubUnionId subUnionId = subUnionIdConverter.covert(skuMetadata.getSubUnionId());
        long buyer = subUnionId.getBuyer(); //下单的人
        long owner = subUnionId.getOwner(); //推广链接归属人
        boolean isRebate = subUnionId.isRebate();


        MemberContainer container = buyer != 0 ?  memberContainerBuilder.build(buyer) : memberContainerBuilder.build(owner);

        //订单归属人，订单归属人只能是自己或者上级
        MemberNode orderOwner = container.find(node -> Objects.equals(node.getUserId(), owner)).stream().findFirst().orElse(null);

        if(orderOwner == null){
            throw BizRuntimeException.from(ResultCode.ERROR, "订单归属人未找到");
        }

        //使用订单生成时的等级，防止计算数据波动
        orderOwner.setIdentify(UserUtils.getUserIdentify(subUnionId.getIdentifyOfOwner()));

        //是否有购买人，如果buyer不为0的情况
        MemberNode buyerNode = container.find(node -> Objects.equals(node.getUserId(), buyer)).stream().findFirst().orElse(null);

        //使用订单生成时的等级
        if(buyerNode != null){
            buyerNode.setIdentify(UserUtils.getUserIdentify(subUnionId.getIdentifyOfBuyer()));
        }

        //订单有返利，计算返利（返利只会返还给购买人）
        //订单有返利，计算返利（返利只会返还给购买人）
        if (isRebate && buyerNode != null) {
            //小程序自购
            UserIdentify identify = buyerNode.getIdentify();
            MemberCommissionInfo rebateOfBuyer = new MemberCommissionInfo();
            rebateOfBuyer.setRemark("自购返利: " + (identify.getRebateRate().multiply(BigDecimal.valueOf(100))));
            rebateOfBuyer.setEstimateRebateFee(identify.getRebateRate().multiply(BigDecimal.valueOf(skuMetadata.getEstimateFee())));
            rebateOfBuyer.setActualRebateFee(identify.getRebateRate().multiply(BigDecimal.valueOf(skuMetadata.getActualFee())));
            rebateOfBuyer.setFeeType(FeeType.REBATE.getType());
            buyerNode.add(rebateOfBuyer);
        }



        MemberInfo memberInfo = container.buildMemberInfo(orderOwner);
        UserIdentify ownerIdentify = orderOwner.getIdentify();

        if(ownerIdentify == UserIdentify.REGISTERED){
            throw BizRuntimeException.from(ResultCode.ERROR, "订单归属人不能为注册会员");
        }

        //推广人获得佣金
        MemberCommissionInfo commissionInfoOfRoot =  new MemberCommissionInfo();
        commissionInfoOfRoot.setEstimateCommissionFee(ownerIdentify.getCommissionRate().multiply(BigDecimal.valueOf(skuMetadata.getEstimateFee())));
        commissionInfoOfRoot.setActualCommissionFee(ownerIdentify.getCommissionRate().multiply(BigDecimal.valueOf(skuMetadata.getActualFee())));
        commissionInfoOfRoot.setRemark(String.format("推广人获得佣金的：%s%%", (ownerIdentify.getCommissionRate().multiply(BigDecimal.valueOf(100)).toString())));
        commissionInfoOfRoot.setFeeType(FeeType.COMMISSION.getType());
        orderOwner.add(commissionInfoOfRoot);


        if(ownerIdentify == UserIdentify.SUPER){
            calForSuperMember(memberInfo,commissionInfoOfRoot);
        }else if(ownerIdentify == UserIdentify.TEACHER){
            calForTeacherMember(memberInfo, commissionInfoOfRoot);
        }

        return container;
    }

    /**
     * 计算奖励分成
     */
    private void calForSuperMember(MemberInfo memberInfo, MemberCommissionInfo commissionInfoOfRoot){
        if(memberInfo.getDirectlySuperMember() != null){
            //超级会员获取直属超级会员的20%
            BigDecimal firstSuperRate = new BigDecimal("0.15");
            MemberCommissionInfo commissionInfo = new MemberCommissionInfo();
            commissionInfo.setEstimateAwardFee(firstSuperRate.multiply(commissionInfoOfRoot.getEstimateCommissionFee()));
            commissionInfo.setActualAwardFee(firstSuperRate.multiply(commissionInfoOfRoot.getActualCommissionFee()));
            commissionInfo.setRemark("直属超级会员获得奖励：20%");
            commissionInfo.setFeeType(FeeType.AWARD.getType());
            memberInfo.getDirectlySuperMember().add(commissionInfo);

        }

        if(memberInfo.getDirectlyTeacher() != null){
            //超级会员直属导师奖励30%
            BigDecimal directlyTeacherRate = new BigDecimal("0.25");
            MemberCommissionInfo commissionInfoOfDirectlyTeacher = new MemberCommissionInfo();
            commissionInfoOfDirectlyTeacher.setEstimateAwardFee(directlyTeacherRate.multiply(commissionInfoOfRoot.getEstimateCommissionFee()));
            commissionInfoOfDirectlyTeacher.setActualAwardFee(directlyTeacherRate.multiply(commissionInfoOfRoot.getActualCommissionFee()));
            commissionInfoOfDirectlyTeacher.setRemark("直属导师获取奖励：30%");
            commissionInfoOfDirectlyTeacher.setFeeType(FeeType.AWARD.getType());
            memberInfo.getDirectlyTeacher().add(commissionInfoOfDirectlyTeacher);
        }else if(memberInfo.getIndirectlyTeacher() != null){
            //超级会员非直属导师奖励20%
            BigDecimal indirectlyTeacherRate = new BigDecimal("0.15");
            MemberCommissionInfo commissionInfoOfDirectlyTeacher = new MemberCommissionInfo();
            commissionInfoOfDirectlyTeacher.setEstimateAwardFee(indirectlyTeacherRate.multiply(commissionInfoOfRoot.getEstimateCommissionFee()));
            commissionInfoOfDirectlyTeacher.setActualAwardFee(indirectlyTeacherRate.multiply(commissionInfoOfRoot.getActualCommissionFee()));
            commissionInfoOfDirectlyTeacher.setRemark("非直属导师获取奖励：20%");
            commissionInfoOfDirectlyTeacher.setFeeType(FeeType.AWARD.getType());
            memberInfo.getIndirectlyTeacher().add(commissionInfoOfDirectlyTeacher);
        }

        //超级会员的直属或非直属导师的直属导师获得50%奖励
        if(memberInfo.getDirectlyTeacherOfTeacher() != null){
            MemberNode node = memberInfo.getDirectlyTeacher() != null ? memberInfo.getDirectlyTeacher() : memberInfo.getIndirectlyTeacher();
            MemberCommissionInfo commissionInfoOfTeacher = findFirstNotNull(node);

            BigDecimal directlyTeacherRate = new BigDecimal("0.5");
            MemberCommissionInfo commissionInfoOfDirectlyTeacher = new MemberCommissionInfo();
            commissionInfoOfDirectlyTeacher.setEstimateAwardFee(directlyTeacherRate.multiply(commissionInfoOfTeacher.getEstimateAwardFee()));
            commissionInfoOfDirectlyTeacher.setActualAwardFee(directlyTeacherRate.multiply(commissionInfoOfTeacher.getActualAwardFee()));
            commissionInfoOfDirectlyTeacher.setRemark("导师的直属导师获得该导师的 50% 奖励收益");
            commissionInfoOfDirectlyTeacher.setFeeType(FeeType.AWARD.getType());
            memberInfo.getDirectlyTeacherOfTeacher().add(commissionInfoOfDirectlyTeacher);
        }
    }

    /**
     * 计算奖励分成
     */
    private void calForTeacherMember(MemberInfo memberInfo, MemberCommissionInfo commissionInfoOfRoot) {
        if(memberInfo.getDirectlyTeacher() != null){
            //导师的直属导师奖励6%
            BigDecimal directlyTeacherRate = new BigDecimal("0.06");
            MemberCommissionInfo commissionInfoOfDirectlyTeacher = new MemberCommissionInfo();
            commissionInfoOfDirectlyTeacher.setEstimateAwardFee(directlyTeacherRate.multiply(commissionInfoOfRoot.getEstimateCommissionFee()));
            commissionInfoOfDirectlyTeacher.setActualAwardFee(directlyTeacherRate.multiply(commissionInfoOfRoot.getActualCommissionFee()));
            commissionInfoOfDirectlyTeacher.setRemark("导师的直属导师奖励 6%");
            commissionInfoOfDirectlyTeacher.setFeeType(FeeType.AWARD.getType());
            memberInfo.getDirectlyTeacher().add(commissionInfoOfDirectlyTeacher);
        }

        //直属导师的直属导师获得50%奖励
        if(memberInfo.getDirectlyTeacher() != null && memberInfo.getDirectlyTeacherOfTeacher() != null){
            MemberNode node = memberInfo.getDirectlyTeacher();
            MemberCommissionInfo commissionInfoOfTeacher = findFirstNotNull(node);

            BigDecimal directlyTeacherRate = new BigDecimal("0.5");
            MemberCommissionInfo commissionInfoOfDirectlyTeacher = new MemberCommissionInfo();
            commissionInfoOfDirectlyTeacher.setEstimateAwardFee(directlyTeacherRate.multiply(commissionInfoOfTeacher.getEstimateAwardFee()));
            commissionInfoOfDirectlyTeacher.setActualAwardFee(directlyTeacherRate.multiply(commissionInfoOfTeacher.getActualAwardFee()));
            commissionInfoOfDirectlyTeacher.setFeeType(FeeType.AWARD.getType());
            commissionInfoOfDirectlyTeacher.setRemark("导师的直属导师获得该导师的 50% 奖励收益");
            memberInfo.getDirectlyTeacherOfTeacher().add(commissionInfoOfDirectlyTeacher);
        }
    }


    public static MemberCommissionInfo findFirstNotNull(MemberNode memberNode){
        Optional<MemberCommissionInfo> optional = memberNode.findFirst();
        if(!optional.isPresent()){
            throw BizRuntimeException.from(ResultCode.NOT_FOUND, "没有找到佣金记录");
        }

        return optional.get();
    }


}
