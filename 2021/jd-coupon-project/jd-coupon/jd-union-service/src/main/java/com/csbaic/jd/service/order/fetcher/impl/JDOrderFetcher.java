package com.csbaic.jd.service.order.fetcher.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.dto.GoodsOrder;
import com.csbaic.jd.dto.GoodsOrderQuery;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.jd.service.GoodsService;
import com.csbaic.jd.service.order.fetcher.FetchRequest;
import com.csbaic.jd.service.order.fetcher.OrderFetchException;
import com.csbaic.jd.service.order.fetcher.OrderFetcher;
import com.csbaic.jd.service.order.handle.OrderMetadata;
import com.csbaic.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoField.*;

@Slf4j
@Component
public class JDOrderFetcher implements OrderFetcher {


    private static final DateTimeFormatter hourFormatter = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendValue(YEAR, 4)
            .appendValue(MONTH_OF_YEAR, 2)
            .appendValue(DAY_OF_MONTH, 2)
            .appendValue(HOUR_OF_DAY, 2)
            .appendValue(MINUTE_OF_HOUR, 2)
            .toFormatter();



    @Autowired
    private GoodsService goodsService;



    @Override
    public List<OrderMetadata> fetch(FetchRequest fetchRequest) throws OrderFetchException {
        if(fetchRequest.getBegin() == null){
            throw BizRuntimeException.from(ResultCode.ERROR, "订单时间不能为空");
        }

        if(fetchRequest.getEnd() == null){
            throw BizRuntimeException.from(ResultCode.ERROR, "订单时间不能为空");
        }


        LocalDateTime begin = fetchRequest.getBegin();
        LocalDateTime end= fetchRequest.getEnd();

        if(begin.isAfter(end)){
            throw BizRuntimeException.from(ResultCode.ERROR, "订单时间区间不正确");
        }

        //todo 按小时获取订单
        GoodsOrderQuery query = new GoodsOrderQuery();
        query.setType(fetchRequest.getType());
        query.setTime(hourFormatter.format(begin));
        query.setPageIndex(1);
        query.setPageSize(20);

        log.info("Sync order:   {}",  fetchRequest);

        List<GoodsOrder> metadata = new ArrayList<>();

        try{
            IPage<GoodsOrder> goodsOrders =  goodsService.queryGoodsOrder( query);

            while (!goodsOrders.getRecords().isEmpty()){
                metadata.addAll(goodsOrders.getRecords());
                query.setPageIndex((int) goodsOrders.getCurrent() + 1);
                goodsOrders = goodsService.queryGoodsOrder(query);
            }

//            metadata = flatGoodsOrder(metadata);
        }catch (Exception e){
            log.error("Sync err: date {}", fetchRequest);
            log.error("Sync err", e);
            throw new OrderFetchException(e);
        }

        return new ArrayList<>(metadata);
    }

    /**
     * 删除父订单
     * @param order
     * @return
     */
    private static List<GoodsOrder> flatGoodsOrder(List<GoodsOrder> order) {
        if (order == null || order.isEmpty()) {
            return order;
        }

        List<GoodsOrder> result = new ArrayList<>(order);
        for (GoodsOrder o : order) {
            if (o.getParentId() != null && o.getParentId() != 0) {
                result = result.stream()
                        .filter(goodsOrder -> !Objects.equals(o.getParentId(), goodsOrder.getOrderId()))
                        .collect(Collectors.toList());
            }
        }

        return result;

    }
}
