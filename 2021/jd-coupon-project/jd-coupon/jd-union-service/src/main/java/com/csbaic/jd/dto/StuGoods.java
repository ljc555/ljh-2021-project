package com.csbaic.jd.dto;

import com.csbaic.jd.service.goods.GoodsMetadata;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 *  学生价商品
 */
@Data
public class StuGoods implements GoodsMetadata {

    /**
     * sku名称（商品名称）
     */
    private String skuName;
    /**
     * skuId （商品id）
     */
    private Long skuId;

    /**
     * 图片地址
     */
    private String imageUrl;

    /**
     * 是否学生价商品。 1：是学生价商品。 0：不是学生价商品。
     */
    private Integer isStuPrice;

    /**
     * 京东价
     */
    private Double jdPrice;

    /**
     * 学生专享价
     */
    private Double studentPrice;

    /***
     * 专享价促销开始时间（时间戳：毫秒）
     */
    private Long stuPriceStartTime;

    /**
     * 专享价促销结束时间（时间戳：毫秒）
     */
    private Long stuPriceEndTime;

    /**
     * 一级类目id
     */
    private Long cid1Id;

    /**
     * 二级类目id
     */
    private Long cid2Id;

    /**
     * 三级类目id
     */
    private Long cid3Id;
    private String cid1Name;
    private String cid2Name;
    private String cid3Name;

    /**
     * 通用佣金比例，百分比
     */
//    private Double commissionShare;

    /**
     * 通用佣金
     */
    private Double commission;

    /**
     *
     * 佣金
     */
    @ApiModelProperty(value = "佣金提示字段")
    private String commissionHint;


    @ApiModelProperty(value = "超级会员佣金提示字段")
    private String superMemberCommissionHint;


    /***
     * 是否自营。g=自营，p=pop
     */
    private String owner;

    /**
     * 30天引入订单量（spu）
     */
    private Long inOrderCount30Days;

    /***
     * 30天支出佣金（spu）
     */
    private Double inOrderComm30Days;

}