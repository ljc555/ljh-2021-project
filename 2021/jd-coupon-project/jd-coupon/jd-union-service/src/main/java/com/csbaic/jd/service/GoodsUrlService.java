package com.csbaic.jd.service;

import com.csbaic.jd.dto.*;

/**
 * 推广链接服务
 */
public interface GoodsUrlService {

    /**
     * 创建商品推广短链接
     */
    GoodsRecUrl createGoodsShortUrl(Long userId, CreateGoodsShortUrl goodsUrl);

    /**
     * 创建购买推广短链接
     */
    GoodsRecUrl createPurchaseShortUrl(Long userId, CreatePurchaseShortUrl goodsUrl);



    /**
     * 将链接转换为自己的推广链接
     * @param userId
     */
    ConvertUrlResult createConvertedUrl(Long userId, ConvertUrlContent convertUrlContent );

}
