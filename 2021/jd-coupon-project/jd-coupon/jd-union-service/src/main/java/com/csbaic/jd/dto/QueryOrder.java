package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class QueryOrder {


    /**
     * 分页
     */
    @ApiModelProperty("分页")
    private Integer pageIndex;

    /**
     * 分页大小
     */
    @ApiModelProperty("分页大小")
    private Integer pageSize;


    /**
     * 查询类型
     */
    @ApiModelProperty(value = "查询类型(1: 我购买的，2：我推广的）", allowableValues = "1,2", example = "1")
    private int type;


    /**
     * 订单有效码
     */
    @ApiModelProperty(value = "状态码：1 （所有订单）2（待付款），3（已付款），4（已完成），5（无效订单），6（已取消）", allowableValues = "1, 2,3,4,5,6", example = "1")
    private Integer status;

    /**
     * 开时时间
     */
    @ApiModelProperty(value = "查询开始时间，时间格式 yyyyMMdd", example = "20200305")
    private String begin;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "查询结束时间，时间格式 yyyyMMdd", example = "20200405")
    private String end;
}
