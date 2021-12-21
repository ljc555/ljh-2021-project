package com.csbaic.jd.service.commission;

import com.csbaic.jd.service.order.handle.OrderMetadata;
import com.csbaic.jd.service.team.MemberContainer;

/**
 * Created by yjwfn on 2020/2/16.
 */
public interface CommissionStrategy {

    /**
     * 计算佣金
     * @param orderMetadata  訂單信息
     * @param skuMetadata 商品信息
     */
    MemberContainer cal(OrderMetadata orderMetadata, OrderMetadata.SkuMetadata skuMetadata);
}
