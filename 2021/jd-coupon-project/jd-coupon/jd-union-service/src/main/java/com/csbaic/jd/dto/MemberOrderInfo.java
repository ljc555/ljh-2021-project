package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

@ApiModel("成员订单")
@Data
public class MemberOrderInfo
{

    /**
     * 今日订单量
     */
    @ApiModelProperty("今日订单量")
    private Integer countForToday;

    /**
     * 昨日订单量
     */
    @ApiModelProperty("今日订单量")
    private Integer countForYesterday;


}
