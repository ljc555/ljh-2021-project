package com.csbaic.jd.service.order.fetcher.impl;


import com.csbaic.jd.service.order.fetcher.OrderFetcherScheduler;
import com.csbaic.jd.service.IOptionService;
import com.csbaic.jd.service.option.Options;
import com.csbaic.jd.service.order.fetcher.FetchRequest;
import com.csbaic.jd.service.order.fetcher.OrderFetchException;
import com.csbaic.jd.service.order.fetcher.OrderFetcher;
import com.csbaic.jd.service.order.handle.OrderHandler;
import com.csbaic.jd.service.order.handle.OrderHandlerFactory;
import com.csbaic.jd.service.order.handle.OrderMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;

@Slf4j
@Component
public class JDOrderFetcherScheduler implements OrderFetcherScheduler  {


    @Autowired
    private OrderHandlerFactory orderHandlerFactory;


    @Autowired
    private OrderFetcher orderFetcher;

    @Autowired
    private IOptionService optionService;


    private  Thread workThread;

    private final Object lock = new Object();



    @Override
    public void schedule() {
        if(workThread != null){
            return;
        }

        log.info("Start JDOrderFetcherScheduler: {}", LocalDateTime.now());
        workThread = new Thread(this::scheduleForever);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if(workThread != null){
                workThread.interrupt();
            }
        }));

        workThread.start();
    }

    private void scheduleForever(){

        final DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyyMMddHHmmss")
                .toFormatter();

        LocalDateTime syncDate = optionService.getLocalDateTime(Options.OPT_SYS_CURRENT_SYNC_TIME, "yyyyMMddHHmmss");
        LocalDateTime startAt = optionService.getLocalDateTime(Options.OPT_SYS_ORDER_SYNC_START_TIME, "yyyyMMddHHmmss");

        if(syncDate != null){
            //同步下一分钟的订单
            startAt = syncDate.plusMinutes(1);
        }

        while (!Thread.interrupted()){
            try {
                List<LocalDateTime> records = scheduleImpl(startAt);


                if(!records.isEmpty()){
                    LocalDateTime last = records.get(records.size() - 1);
                    optionService.setOption(Options.OPT_SYS_CURRENT_SYNC_TIME, dateTimeFormatter.format(last));
                }

                //等待到下一分钟再同步数据
                startAt =  !records.isEmpty() ? records.get(records.size() - 1).plusMinutes(1) : startAt.plusMinutes(1);
                LocalDateTime now  = LocalDateTime.now();
                Duration duration  = Duration.between(now, startAt);

                if(!duration.isNegative()){
                    synchronized (lock){
                        lock.wait(duration.toMillis());
                    }
                }


            } catch (InterruptedException e) {
                log.error("sync err", e);
                return;
            }catch (Exception e){
                log.error("sync err", e);
            }
        }

    }



    /**
     * 获取订单、同步订单
     */
    private List<LocalDateTime> scheduleImpl(LocalDateTime startAt) throws InterruptedException{
        log.debug("Start sync order at {}", startAt);

        //获取今天最后一次同步记录
        int minutesOfDay = startAt.get(MINUTE_OF_DAY);
        LocalDate syncDate =  startAt.toLocalDate();

        LocalDateTime now = LocalDateTime.now()
                .withSecond(0);

        LocalDateTime currentSyncDateTime = LocalDateTime.of(syncDate, LocalTime.ofSecondOfDay(minutesOfDay * 60))
                .withSecond(0);

        OrderHandler orderHandler = orderHandlerFactory.create();
        FetchRequest request = new FetchRequest();
        request.setType(3);

        List<LocalDateTime> records = new ArrayList<>();

        int delay = optionService.getInt(Options.OPT_SYS_ORDER_SYNC_DELAY);

        while (
                (currentSyncDateTime.isBefore(now) || currentSyncDateTime.isEqual(now))
                && !Thread.interrupted()
        ){
            LocalDateTime delayed = currentSyncDateTime.minusMinutes(delay);
            request.setBegin(delayed.withSecond(0));
            request.setEnd(delayed.withSecond(59));
            log.debug("fetch order: {}" , request);

            List<OrderMetadata> orderMetadata =  null;


            try{
                Thread.sleep(500); //限制调用速度
                orderMetadata  = orderFetcher.fetch(request);
            } catch (OrderFetchException ex){
                log.error("Fetch order on failure with params {} ", request);
                log.error("Retry fetch order forever");
                //订单同步失败无限重试
                continue;
            }


            handle(orderHandler, orderMetadata);
            records.add(currentSyncDateTime);
            currentSyncDateTime = currentSyncDateTime.plusMinutes(1);
        }

        return records;
    }

    private static void handle(OrderHandler orderHandler, List<OrderMetadata> orderMetadata){
        log.debug("fetch order: {}" , orderMetadata);
        if(orderMetadata != null){
            for(OrderMetadata metadata : orderMetadata){
                orderHandler.handle(metadata);
            }
        }
    }

}
