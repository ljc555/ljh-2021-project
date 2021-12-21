package com.csbaic.jd.service.order.handle;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by yjwfn on 2020/2/15.
 */
@Slf4j
public class DefaultOrderHandlerChain implements OrderHandlerChain {

    /**
     * 订单信息
     */
    private OrderMetadata orderMetadata;

    /**
     * 订单过滤器
     */
    private List<OrderFilter> filters;

    /**
     * 过滤器位置索引
     */
    private int index;

    private DefaultOrderHandlerChain(OrderMetadata orderMetadata, List<OrderFilter> filters, int index) {
        this.orderMetadata = orderMetadata;
        this.filters = filters;
        this.index = index;
    }


    public DefaultOrderHandlerChain(List<OrderFilter> filters) {
        this.filters = filters;
    }


    public void handle(OrderMetadata orderMetadata) {

        /*
            过滤器已执行完毕
         */
        if(index >= filters.size()){
            return;
        }


        OrderFilter filter = filters.get(index);

        if(filter == null){
            throw new NullPointerException("OrderFilter is null at index #" + index);
        }

        DefaultOrderHandlerChain nextChain = new DefaultOrderHandlerChain(orderMetadata, filters, index + 1);
        log.info("Execute filter: {}", filter.getClass().getName());
        filter.filter(nextChain);
    }

    @Override
    public OrderMetadata getOrderMetadata() {
        return orderMetadata;
    }
}
