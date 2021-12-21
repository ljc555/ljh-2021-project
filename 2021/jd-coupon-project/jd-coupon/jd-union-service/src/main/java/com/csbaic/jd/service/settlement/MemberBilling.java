package com.csbaic.jd.service.settlement;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class MemberBilling {


    /**
     * 返利
     */
    private BigDecimal estimateRebateFee = BigDecimal.ZERO;

    /**
     * 平台奖励
     */
    private BigDecimal actualRebateFee = BigDecimal.ZERO;


    /**
     * 返利
     */
    private BigDecimal estimateAwardFee = BigDecimal.ZERO;


    /**
     * 平台奖励
     */
    private BigDecimal actualAwardFee = BigDecimal.ZERO;


    /**
     * 预估佣金
     */
    private BigDecimal estimateCommissionFee = BigDecimal.ZERO;

    /**
     * 实际佣金
     */
    private BigDecimal actualCommissionFee = BigDecimal.ZERO;



}
