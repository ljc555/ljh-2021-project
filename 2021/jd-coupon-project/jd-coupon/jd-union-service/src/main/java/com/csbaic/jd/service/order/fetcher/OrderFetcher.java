package com.csbaic.jd.service.order.fetcher;

import com.csbaic.jd.service.order.handle.OrderMetadata;

import java.util.List;

/**
 * 订单获取器
 */
public interface OrderFetcher  {


    /**
     * 获取订单
     */
    List<OrderMetadata> fetch(FetchRequest request) throws OrderFetchException;

}
