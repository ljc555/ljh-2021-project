package com.csbaic.jd.service.order.handle.filter;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.csbaic.jd.entity.OrderSyncRecordEntity;
import com.csbaic.jd.enums.SuccessOrFailureStatus;
import com.csbaic.jd.service.IOrderSyncRecordService;
import com.csbaic.jd.service.order.handle.OrderHandlerChain;
import com.csbaic.jd.service.order.handle.OrderMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.csbaic.jd.service.order.handle.filter.FilterOrder.ORDER_SYNC_STATUS_RECORDER;

/**
 * Created by yjwfn on 2020/2/15.
 */
@Slf4j
@Component
@Order(ORDER_SYNC_STATUS_RECORDER)
public class SyncStatusFilter implements OrderHandlerChain.OrderFilter {

    private final IOrderSyncRecordService orderSyncRecordService;

    public SyncStatusFilter(IOrderSyncRecordService orderSyncRecordService) {
        this.orderSyncRecordService = orderSyncRecordService;
    }

    @Override
    public void filter(OrderHandlerChain chain) {

        OrderMetadata orderMetadata = chain.getOrderMetadata();
        OrderSyncRecordEntity syncRecordEntity = orderSyncRecordService
                .getOne(Wrappers.<OrderSyncRecordEntity>query().eq(OrderSyncRecordEntity.ORDER_ID, orderMetadata.getOrderId()));

        if(syncRecordEntity == null){
            syncRecordEntity = new OrderSyncRecordEntity();
        }

        syncRecordEntity.setOrderId(orderMetadata.getOrderId());
        /*
            捕获所有的导异常，保存同步状态
         */
        try{
            chain.handle(orderMetadata);
            syncRecordEntity.setStatus(SuccessOrFailureStatus.SUCCESS.ordinal());
        }catch (Throwable e){
            log.error("sync order fail", e);
            syncRecordEntity.setStatus(SuccessOrFailureStatus.FAILURE.ordinal());
        }finally {
            orderSyncRecordService.saveOrUpdate(syncRecordEntity);
        }
    }
}
