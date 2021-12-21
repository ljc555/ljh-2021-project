package com.csbaic.jd.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel
public class OrderSkuInfo {

    @ApiModelProperty("商品ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long skuId;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("用户id")
    private Long userId;


    /**
     * 京东订单id
     */
    @ApiModelProperty("京东订单id")
    private Long orderId;


    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String skuName;

    /**
     * sku图片
     */
    @ApiModelProperty("sku图片")
    private String skuImage;

    /**
     * 商品数量
     */
    @ApiModelProperty("商品数量")
    private Long skuNum;


//    /**
//     * sku维度的有效码（-1：未知,2.无效-拆单,3.无效-取消,4.无效-京东帮帮主订单,5.无效-账号异常,6.无效-赠品类目不返佣,7.无效-校园订单,8.无效-企业订单,9.无效-团购订单,10.无效-开增值税专用发票订单,11.无效-乡村推广员下单,12.无效-自己推广自己下单,13.无效-违规订单,14.无效-来源与备案网址不符,15.待付款,16.已付款,17.已完成,18.已结算（5.9号不再支持结算状态回写展示））
//     */
//    @ApiModelProperty("sku维度的有效码（-1：未知,2.无效-拆单,3.无效-取消,4.无效-京东帮帮主订单,5.无效-账号异常,6.无效-赠品类目不返佣,7.无效-校园订单,8.无效-企业订单,9.无效-团购订单,10.无效-开增值税专用发票订单,11.无效-乡村推广员下单,12.无效-自己推广自己下单,13.无效-违规订单,14.无效-来源与备案网址不符,15.待付款,16.已付款,17.已完成,18.已结算（5.9号不再支持结算状态回写展示））")
//    private Integer validCode;

    @ApiModelProperty("validCode文字描述")
    private String validCodeDesc;

    /**
     * 2：同店；3：跨店
     */
    @ApiModelProperty("同店或跨店")
    private String traceType;


    @ApiModelProperty("支付金额")
    private BigDecimal price;

    @ApiModelProperty("订单标签")
    private List<String> tag;

    /**
     *  预估计佣金额
     */
    @ApiModelProperty("预估计佣金额")
    private BigDecimal estimateCosPrice;

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


}
