package com.csbaic.jd.service.order.handle;

/**
 * Created by yjwfn on 2020/2/15.
 */
public interface OrderHandlerChain   {


    /**
     * 获取订单信息
     * @return
     */
    OrderMetadata getOrderMetadata();

    /**
     * 处理订单
     * @param orderMetadata
     */
    void handle(OrderMetadata orderMetadata);

    /**
     * 订单过滤器
     */
    interface OrderFilter {


        /**
         * 过滤并处理
         * @param chain
         */
        void filter(OrderHandlerChain chain);
    }
}
