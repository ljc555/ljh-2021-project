package com.csbaic.jd.service.order.handle;

/**
 * 使用{@link DefaultOrderHandlerChain}处理订单
 */
public class ChainedOrderHandler implements OrderHandler {

    private final OrderHandlerChain orderHandlerChain;

    public ChainedOrderHandler(OrderHandlerChain orderHandlerChain) {
        this.orderHandlerChain = orderHandlerChain;
    }


    @Override
    public void handle(OrderMetadata orderMetadata) {
        orderHandlerChain.handle(orderMetadata);
    }
}
