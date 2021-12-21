package com.csbaic.jd.service.order.fetcher;

import com.csbaic.jd.service.order.fetcher.OrderFetcher;

/**
 * {@link OrderFetcher} 调度器
 */
public interface OrderFetcherScheduler {

    /**
     * 开始获取订单
     */
    void schedule();

}
