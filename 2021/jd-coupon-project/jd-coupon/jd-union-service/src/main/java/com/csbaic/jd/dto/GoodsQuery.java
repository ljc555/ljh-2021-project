package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("查询商品")
@Data
public class GoodsQuery {

    /**
     * 一级分类
     */
    @ApiModelProperty("一级分类")
    private Long cid1;

    /**
     * 二级分类
     */
    @ApiModelProperty(" 二级分类")
    private Long cid2;

    /**
     * 三级分类
     */
    @ApiModelProperty("三级分类")
    private Long cid3;
    /**
     * 页码
     */
    @ApiModelProperty("页码")
    private Integer pageIndex;
    /**
     * 页大小
     */
    @ApiModelProperty("页大小")
    private Integer pageSize;
    /***
     * 查询商品的sku
     */
    @ApiModelProperty("查询商品的sku")
    private Long[] skuIds;
    /**
     * 查询关键字
     */
    @ApiModelProperty("查询关键字")
    private String keyword;
    /**
     * 最底价格
     */
    @ApiModelProperty("最底价格")
    private Double pricefrom;
    /**
     * 最高价
     */
    @ApiModelProperty("最高价")
    private Double priceto;
    /**
     * 开始佣金比率
     */
    @ApiModelProperty("开始佣金比率")
    private Integer commissionShareStart;
    /**
     * 结束佣金比率
     */
    @ApiModelProperty("结束佣金比率")
    private Integer commissionShareEnd;
    /**
     * 来源（g=自营，p=pop）
     */
    @ApiModelProperty("来源（g=自营，p=pop）")
    private String owner;

    /**
     * 排序字段(price：单价, commissionShare：佣金比例, commission：佣金， inOrderCount30Days：30天引单量， inOrderComm30Days：30天支出佣金)
     */
    @ApiModelProperty("排序字段(price：单价, commissionShare：佣金比例, commission：佣金， inOrderCount30Days：30天引单量， inOrderComm30Days：30天支出佣金)")
    private String sortName;
    /**
     * asc,desc升降序,默认降序
     */
    @ApiModelProperty("asc,desc升降序,默认降序")
    private String sort;
    /**
     * 是否是优惠券商品，1：有优惠券，0：无优惠券
     */
    @ApiModelProperty("是否是优惠券商品，1：有优惠券，0：无优惠券")
    private Integer isCoupon;
    /**
     * 是否是拼购商品，1：拼购商品，0：非拼购商品
     */
    @ApiModelProperty("是否是拼购商品，1：拼购商品，0：非拼购商品")
    private Integer isPG;
    /**
     *
     * 拼购价格区间开始
     */
    @ApiModelProperty("拼购价格区间开始")
    private Double pingouPriceStart;
    /***
     * 拼购价格区间结束
     */
    @ApiModelProperty("拼购价格区间结束")
    private Double pingouPriceEnd;
    /***
     * 是否是爆款，1：爆款商品，0：非爆款商品
     */
    @ApiModelProperty("是否是爆款，1：爆款商品，0：非爆款商品")
    private Integer isHot;
    /***
     * 品牌code
     */
    @ApiModelProperty("品牌code")
    private String brandCode;
    /**
     * 店铺Id
     */
    @ApiModelProperty("店铺Id")
    private Integer shopId;
    /**
     * 1：查询内容商品；其他值过滤掉此入参条件。
     */
    @ApiModelProperty("1：查询内容商品；其他值过滤掉此入参条件。")
    private Integer hasContent;
    /**
     * 1：查询有最优惠券商品；其他值过滤掉此入参条件。
     */
    @ApiModelProperty("1：查询有最优惠券商品；其他值过滤掉此入参条件。")
    private Integer hasBestCoupon;
    /**
     * 联盟id_应用iD_推广位id
     */
    @ApiModelProperty("联盟id_应用iD_推广位id")
    private String pid;
    /**
     * 支持出参数据筛选，逗号','分隔，目前可用：videoInfo
     */
    @ApiModelProperty("支持出参数据筛选，逗号','分隔，目前可用：videoInfo")
    private String fields;

}
