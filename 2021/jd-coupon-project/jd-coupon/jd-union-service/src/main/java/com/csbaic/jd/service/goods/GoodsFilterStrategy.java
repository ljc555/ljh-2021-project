package com.csbaic.jd.service.goods;

/**
 * 商品过虑
 */
public interface GoodsFilterStrategy {

    /**
     * 过滤一些不能显示的商品
     * @param goodsMetadata
     * @return True 商品可以显示，False 商品不可以显示
     */
    boolean apply(GoodsMetadata goodsMetadata);

}
