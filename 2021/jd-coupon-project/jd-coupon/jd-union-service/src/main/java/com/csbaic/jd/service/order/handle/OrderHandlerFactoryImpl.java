package com.csbaic.jd.service.order.handle;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderHandlerFactoryImpl implements OrderHandlerFactory {


    /**
     * 过滤器
     */
    private final List<OrderHandlerChain.OrderFilter> filters;

    public OrderHandlerFactoryImpl(List<OrderHandlerChain.OrderFilter> filters){
        this.filters = filters;
    }

    @Override
    public OrderHandler create() {
        return new ChainedOrderHandler(new DefaultOrderHandlerChain(filters));
    }
}
