package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("成员预估收益")
public class MemberEstimateFeeInfo {

    /**
     * 今日返利收益
     */
    private BigDecimal rebateFeeForToday = BigDecimal.ZERO;

    /**
     * 昨日返利收益
     */
    private BigDecimal rebateFeeForYesterday = BigDecimal.ZERO;

    /**
     * 今日奖励收益
     */
    private BigDecimal awardFeeForToday = BigDecimal.ZERO;

    /**
     * 昨日奖励收益
     */
    private BigDecimal awardFeeForYesterday = BigDecimal.ZERO;
}
