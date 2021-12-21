package com.csbaic.jd.dto;

import com.csbaic.jd.service.goods.GoodsMetadata;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("通用商品分类模型")
@Data
public class Goods implements GoodsMetadata {

    /**
     * 商品id/skuId
     */
    @ApiModelProperty(value = "商品skuId")
    private Long skuId;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String skuName;

    /**
     * 商品链接
     */
    @ApiModelProperty(value = "商品满地页地址")
    private String materialUrl;

    /**
     * 优惠券
     */
    @ApiModelProperty(value = "商品优惠券列表")
    private List<Coupon> coupons;

    /**
     * 评论数量
     */
    @ApiModelProperty(value = "评论数量")
    private Long comments;

    /**
     * 好评率
     */
    @ApiModelProperty(value = "商品好评率")
    private Double goodCommentsShare;


    /**
     * 商品价格
     */
    @ApiModelProperty(value = "商品价格信息" )
    private PriceInfo priceInfo;

    /**
     * 商品图片地址，第一个为主图
     */
    @ApiModelProperty(value = "商品图片" )
    private List<String> images;

    /**
     * 类别信息
     */
    @ApiModelProperty(value = "商品分类（京东分类）")
    private CategoryInfo categoryInfo;

    /**
     * 佣金信息
     */
    @ApiModelProperty(value = "佣金信息")
    private CommissionInfo commissionInfo;

    /**
     * 店铺信息
     */
    @ApiModelProperty(value = "商品店铺信息")
    private ShopInfo shopInfo;

    /**
     * 是否爆款
     */
    @ApiModelProperty(value = "是否为爆款商品")
    private Integer isHot;

    /**
     * 商品归属（g=自营，p=pop）
     */
    @ApiModelProperty(value = "商品归属（g=自营，p=pop）")
    private String owner;

    /**
     * 商品归属（g=自营，p=pop）
     */
    @ApiModelProperty(value = "商品归属（京东自营，京东）")
    private String ownerDesc;

    /**
     * 拼购信息
     */
    @ApiModelProperty(value = "拼购信息")
    private PinGouInfo pinGouInfo;

    /**
     * 秒杀信息
     */
    @ApiModelProperty(value = "秒杀信息信息")
    private SeckillInfo seckillInfo;

    /**
     * 资源信息
     */
    @ApiModelProperty(value = "资源信息（频道信息）")
    private ResourceInfo resourceInfo;

    /**
     *
     * 30天引单数量(sku维度)
     */
    @ApiModelProperty(value = "30天引单数量(sku维度)")
    private Long inOrderCount30DaysSku;

    /***
     * 30天引单数量
     */
    @ApiModelProperty(value = "30天引单数量")
    private Long inOrderCount30Days;

//    /**
//     * 用户信息
//     */
//    @ApiModelProperty(value = "用户信息")
//    private SimpleMemberUserInfo userInfo;
}
