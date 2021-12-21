package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("会员")
@Data
public class MemberInfo {


    /**
     * 用户信息
     */
    @ApiModelProperty("用户信息")
    private SimpleMemberUserInfo userInfo;

    /**
     * 导师用户信息
     */
    @ApiModelProperty("导师用户信息")
    private SimpleMemberUserInfo teacherInfo;


    /**
     * 成员预估收益
     */
    @ApiModelProperty("成员预估收益")
   private MemberEstimateFeeInfo memberEstimateFeeInfo;


    /**
     * 成员订单
     */
    @ApiModelProperty("成员订单")
    private MemberOrderInfo memberOrderInfo;

    /**
     * 粉丝信息
     */
    @ApiModelProperty("粉丝信息")
    private MemberFansInfo fans;


}
