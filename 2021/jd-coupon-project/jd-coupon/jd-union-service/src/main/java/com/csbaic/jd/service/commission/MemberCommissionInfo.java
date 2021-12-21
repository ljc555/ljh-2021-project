package com.csbaic.jd.service.commission;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Created by yjwfn on 2020/2/15.
 */
@ToString
@Data
public class MemberCommissionInfo {


    /**
     * 返利（返利是返利給購買人）
     */
    private BigDecimal estimateRebateFee = BigDecimal.ZERO;

    /**
     * 返利（返利是返利給購買人）
     */
    private BigDecimal actualRebateFee = BigDecimal.ZERO;



    /**
     * 预估獎勵
     */
    private BigDecimal estimateAwardFee = BigDecimal.ZERO;

    /**
     * 實際獎勵
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


    private Integer feeType;

    /**
     * 佣金备注
     */
    private String remark;

}
