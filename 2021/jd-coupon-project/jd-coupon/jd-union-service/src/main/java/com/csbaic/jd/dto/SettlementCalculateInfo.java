package com.csbaic.jd.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 用户账单计算状态记录
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-20
 */
@Data
public class SettlementCalculateInfo implements Serializable {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 开始时间
     */
    private LocalDate startDate;

    /**
     * 开始时间
     */
    private LocalDate endDate;

    /**
     * 返利佣金（卖货）
     */
    private BigDecimal rebateFee;

    /**
     * 推荐佣金（下线）
     */
    private BigDecimal awardFee;



    /**
     * 佣金
     */
    private BigDecimal commissionFee;



}
