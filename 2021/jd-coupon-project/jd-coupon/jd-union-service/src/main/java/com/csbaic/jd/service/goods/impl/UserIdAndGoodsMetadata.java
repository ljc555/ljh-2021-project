package com.csbaic.jd.service.goods.impl;

import com.csbaic.jd.service.goods.GoodsMetadata;

/**
 * 带等级信息的订单元数据
 */
public class UserIdAndGoodsMetadata implements GoodsMetadata
{

    /**
     * 用户等级
     */
    private final Long userId;

    /**
     * 商品
     */
    private final GoodsMetadata goods;


    public UserIdAndGoodsMetadata(Long userId, GoodsMetadata goods) {
        this.userId = userId;
        this.goods = goods;
    }

    public Long getUserId() {
        return userId;
    }

    public GoodsMetadata getOrigin() {
        return goods;
    }
}
