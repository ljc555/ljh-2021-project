package com.csbaic.jd.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@ToString
@Data
public class SettlementTransactionFlow {


    private Long txId;


    /**
     * 开始时间
     */
    private  LocalDate start;

    /**
     * 结束时间
     */
    private  LocalDate end;

    /**
     * 用户id
     */
    private  Long userId;



    /**
     * 返利
     */
    private BigDecimal rebateFee = BigDecimal.ZERO;

    /**
     * 奖励
     */
    private BigDecimal awardFee = BigDecimal.ZERO;


    /**
     * 佣金
     */
    private BigDecimal commissionFee = BigDecimal.ZERO;

}
