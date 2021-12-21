package com.csbaic.jd.dto;

import com.csbaic.jd.service.goods.GoodsMetadata;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SecKillGoods implements GoodsMetadata {

    private String skuName;
    private Long skuId;
    private String imageUrl;
    private Integer isSecKill;
    private Double oriPrice;
    private Double secKillPrice;
    private Long secKillStartTime;
    private Long secKillEndTime;
    private Long cid1Id;
    private Long cid2Id;
    private Long cid3Id;
    private String cid1Name;
    private String cid2Name;
    private String cid3Name;
//    private Double commissionShare;
    private Double commission;

    /**
     *
     * 佣金
     */
    @ApiModelProperty(value = "佣金提示字段")
    private String commissionHint;


    @ApiModelProperty(value = "超级会员佣金提示字段")
    private String superMemberCommissionHint;


    private String owner;
    private Long inOrderCount30Days;
    private Double inOrderComm30Days;
    private Double jdPrice;
}
