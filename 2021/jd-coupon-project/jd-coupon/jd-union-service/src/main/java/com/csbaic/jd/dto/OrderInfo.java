package com.csbaic.jd.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel
public class OrderInfo {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("订单id")
    private Long orderId;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("用户id")
    private Long userId;


    @ApiModelProperty("订单完成时间(时间戳，毫秒)")
    private LocalDateTime finishTime;

    @ApiModelProperty("下单设备(1:PC,2:无线)")
    private Integer orderEmt;

    @ApiModelProperty("下单时间(时间戳，毫秒)")
    private LocalDateTime orderTime;

    @ApiModelProperty("父单的订单ID，仅当发生订单拆分时返回， 0：未拆分，有值则表示此订单为子订单")
    private Long parentId;

    @ApiModelProperty("下单用户是否为PLUS会员 0：否，1：是")
    private Boolean plus;

//    /**
//     * 订单维度的有效码（-1：未知,2.无效-拆单,3.无效-取消,4.无效-京东帮帮主订单,5.无效-账号异常,6.无效-赠品类目不返佣,7.无效-校园订单,8.无效-企业订单,9.无效-团购订单,10.无效-开增值税专用发票订单,11.无效-乡村推广员下单,12.无效-自己推广自己下单,13.无效-违规订单,14.无效-来源与备案网址不符,15.待付款,16.已付款,17.已完成,18.已结算（5.9号不再支持结算状态回写展示））
//     */
//    @ApiModelProperty("订单维度的有效码（-1：未知,2.无效-拆单,3.无效-取消,4.无效-京东帮帮主订单,5.无效-账号异常,6.无效-赠品类目不返佣,7.无效-校园订单,8.无效-企业订单,9.无效-团购订单,10.无效-开增值税专用发票订单,11.无效-乡村推广员下单,12.无效-自己推广自己下单,13.无效-违规订单,14.无效-来源与备案网址不符,15.待付款,16.已付款,17.已完成,18.已结算（5.9号不再支持结算状态回写展示））")
//    private Integer validCode;

    @ApiModelProperty("validCode文字描述")
    private String validCodeDesc;

    /**
     * 推客的预估返利
     */
    @ApiModelProperty("推客的预估返利")
    private BigDecimal estimateRebateFee;

    /**
     * 推客的预估佣金
     */
    @ApiModelProperty("推客的预估佣金")
    private BigDecimal estimateCommissionFee;


    @ApiModelProperty("订单包含的商品信息列表")
    private List<OrderSkuInfo> skuInfo;


}
