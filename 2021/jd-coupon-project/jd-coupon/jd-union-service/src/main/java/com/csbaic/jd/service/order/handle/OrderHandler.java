package com.csbaic.jd.service.order.handle;

/**
 * Created by yjwfn on 2020/2/15.
 */
public interface OrderHandler {

    /**
     * 订单处理
     * @param orderMetadata 订单信息
     */
    void handle(OrderMetadata orderMetadata);


}
