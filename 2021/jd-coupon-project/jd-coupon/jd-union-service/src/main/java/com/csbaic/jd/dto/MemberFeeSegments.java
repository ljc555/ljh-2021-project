package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 我的收益
 */
@ApiModel("我的收益")
@Data
public class MemberFeeSegments {

    /**
     * 本月收益
     */
    @ApiModelProperty("本月预估收益")
    private List<MemberFeeBlock> thisMonth;

    /**
     * 上月收益
     */
    @ApiModelProperty("上月预估收益")
    private List<MemberFeeBlock> lastMonth;

    /**
     * 最近30天收益
     */
    @ApiModelProperty("最近30天收益")
    private List<MemberFeeBlock> nearly30Days;

    /**
     * 本月和上月结算
     */
    @ApiModelProperty("本月和上月预估结算")
    private MemberFeeBlock billingInfo;
}
