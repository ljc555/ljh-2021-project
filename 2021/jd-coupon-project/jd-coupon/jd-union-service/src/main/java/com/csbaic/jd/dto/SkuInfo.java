package com.csbaic.jd.dto;

import com.csbaic.jd.service.order.handle.OrderMetadata;
import lombok.Data;

@Data
public class SkuInfo implements OrderMetadata.SkuMetadata{

    private Double actualCosPrice;
    private Double actualFee;
    private Double commissionRate;
    private Double estimateCosPrice;
    private Double estimateFee;
    private Double finalRate;
    private Long cid1;
    private Long frozenSkuNum;
    private String pid;
    private Long positionId;
    private Double price;
    private Long cid2;
    private Long siteId;
    private Long skuId;
    private String skuName;
    private Long skuNum;
    private Long skuReturnNum;
    private Double subSideRate;
    private Double subsidyRate;
    private Long cid3;
    private String unionAlias;
    private String unionTag;
    private Integer unionTrafficGroup;
    private Integer validCode;
    private String subUnionId;
    private Integer traceType;
    private Integer payMonth;
    private Long popId;
    private String ext1;
    private Long cpActId;
    private Integer unionRole;
    private String giftCouponKey;
    private Double giftCouponOcsAmount;

}
