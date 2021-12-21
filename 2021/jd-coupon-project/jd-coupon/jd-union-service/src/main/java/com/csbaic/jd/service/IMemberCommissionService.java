package com.csbaic.jd.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.csbaic.jd.dto.MergedMemberCommission;
import com.csbaic.jd.entity.MemberCommissionEntity;
import com.csbaic.jd.service.settlement.MemberBilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 佣金商品记录 服务类
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-15
 */
public interface IMemberCommissionService extends IService<MemberCommissionEntity> {

    /**
     * 获取订单的返利金额
     * @param userId
     * @param orderId
     * @return
     */
    MergedMemberCommission getMergedOrderSkuFee(Long userId, Long orderId, Long skuId);

    /**
     * 计算30天用户佣金数量
     * @return
     */
    MergedMemberCommission getRebateFeeFor30Days(Long userId);


    /**
     * 获取30天用户佣金数量
     * @return
     */
    IPage<MergedMemberCommission> getRebateFeeFor30Days(IPage<MergedMemberCommission> page);


    /**
     * 根据订单的完成时间，查询用户的佣金信息
     * @param page   分布
     * @param userId 用户id
     * @param start 开始日期
     * @param end 结束日期
     * @param validCode 有效code
     * @return
     */
    IPage<MergedMemberCommission> getBillingForUserIdBetweenFinishTimeAndValidCode(IPage<MergedMemberCommission> page, Long userId, LocalDateTime start, LocalDateTime end, Integer validCode);

    /**
     * 根据订单的下单时间，查询用户的佣金信息
     * @param page   分布
     * @param userId 用户id
     * @param start 开始日期
     * @param end 结束日期
     * @param validCode 有效code
     * @return
     */
    IPage<MergedMemberCommission> getBillingForUserIdBetweenOrderTimeAndValidCode(IPage<MergedMemberCommission> page, Long userId, LocalDateTime start, LocalDateTime end, Integer validCode);


    /**
     * 获取用户的预估返利收益
     * @return
     */
    BigDecimal getEstimateRebateFeeBetween(Long userId, LocalDateTime begin, LocalDateTime end);


    /**
     * 获取用户的预估平台奖励收益
     * @return
     */
    BigDecimal getEstimateAwardFeeBetween(Long userId, LocalDateTime begin, LocalDateTime end);


    /**
     * 获取用户的预估推广收益
     * @return
     */
    BigDecimal getEstimateCommissionFeeBetween(Long userId, LocalDateTime begin, LocalDateTime end);

    /**
     * 获取用户的预估所有收益
     * @return
     */
    BigDecimal getTotalEstimateFeeBetween(Long userId, LocalDateTime begin, LocalDateTime end);


    /**
     * 成员预估收益
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    MemberBilling getEstimateFeeBetween(Long userId, LocalDateTime begin, LocalDateTime end);

    /**
     * 成员预估结算收益（根据完成时间计算）
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    MemberBilling getEstimateSettlementFee(Long userId, LocalDateTime begin, LocalDateTime end);


}
