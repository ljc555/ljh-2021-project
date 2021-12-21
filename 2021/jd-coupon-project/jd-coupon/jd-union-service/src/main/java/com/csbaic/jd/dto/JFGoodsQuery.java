package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class JFGoodsQuery {


    /**
     * 频道id：1-好券商品,2-超级大卖场,10-9.9专区,22-热销爆品,24-数码家电,25-超市,26-母婴玩具,27-家具日用,28-美妆穿搭,29-医药保健,30-图书文具,31-今日必推,32-王牌好货,33-秒杀商品,34-拼购商品
     */
    @ApiModelProperty("频道id：1-好券商品,2-超级大卖场,10-9.9专区,22-热销爆品,24-数码家电,25-超市,26-母婴玩具,27-家具日用,28-美妆穿搭,29-医药保健,30-图书文具,31-今日必推,32-王牌好货,33-秒杀商品,34-拼购商品")
    private Integer eliteId;

    @ApiModelProperty("页码")
    private Integer pageIndex;

    @ApiModelProperty("页大小")
    private Integer pageSize;

    @ApiModelProperty("排序字段")
    private String sortName;

    @ApiModelProperty("asc,desc升降序,默认降序")
    private String sort;


    private String pid;


}
