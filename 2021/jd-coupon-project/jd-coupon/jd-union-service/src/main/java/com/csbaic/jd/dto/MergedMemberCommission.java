package com.csbaic.jd.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 合并后的佣金记录
 */
@Data
public class MergedMemberCommission {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 预估推广佣金（卖货）
     */
    private BigDecimal estimateRebateFee;

    /**
     * 预估推荐佣金（下线）
     */
    private BigDecimal estimateAwardFee;

    /**
     * 实际推广佣金（卖货）
     */
    private BigDecimal actualRebateFee;

    /**
     * 实际推荐佣金（下线）
     */
    private BigDecimal actualAwardFee;

    /**
     * 预估佣金
     */
    private BigDecimal estimateCommissionFee;

    /**
     * 实际佣金
     */
    private BigDecimal actualCommissionFee;

}
