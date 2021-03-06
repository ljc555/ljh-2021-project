package com.csbaic.jd.service.goods.detail;

import java.util.List;

/**
 *  商品详情仓库
 */
public interface GoodsDetailRepository {

    /**
     * 获取商品详情
     * @param skuIds 京东skuID串，逗号分割，最多100个，开发示例如param_json={'skuIds':'5225346,7275691'}（非常重要 请大家关注：如果输入的sk串中某个skuID的商品不在推广中[就是没有佣金]，返回结果中不会包含这个商品的信息）
     * @return
     */
    List<GoodsDetail> getGoodsDetail(Long...skuIds);



}
