package com.csbaic.jd.dto;

import lombok.Data;

@Data
public class PromotionGoods {

    private Long skuId;
    private Double unitPrice;
    private String materialUrl;
    private Long endDate;
    private Integer isFreeFreightRisk;
    private Integer isFreeShipping;
    private Double commisionRatioWl;
    private Double commisionRatioPc;
    private String imgUrl;
    private Long vid;
    private String cidName;
    private Double wlUnitPrice;
    private String cid2Name;
    private Integer isSeckill;
    private Long cid2;
    private String cid3Name;
    private Long inOrderCount;
    private Long cid3;
    private Long shopId;
    private Integer isJdSale;
    private String goodsName;
    private Long startDate;
    private Long cid;

}
