package com.csbaic.jd.dto;

import lombok.Data;

@Data
public class SecKillGoodsQuery {

    private Long[] skuIds;
    private Integer pageIndex;
    private Integer pageSize;
    private Integer isBeginSecKill;
    private Double secKillPriceFrom;
    private Double secKillPriceTo;
    private Long cid1;
    private Long cid2;
    private Long cid3;
    private String owner;
    private Double commissionShareFrom;
    private Double commissionShareTo;
    private String sortName;
    private String sort;

}
