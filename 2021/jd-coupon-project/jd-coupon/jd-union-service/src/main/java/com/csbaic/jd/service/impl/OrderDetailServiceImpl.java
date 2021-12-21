package com.csbaic.jd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csbaic.jd.dto.*;
import com.csbaic.jd.entity.OrderDetailEntity;
import com.csbaic.jd.entity.OrderSkuEntity;
import com.csbaic.jd.enums.OrderStatus;
import com.csbaic.jd.enums.OrderValidCode;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.jd.mapper.OrderDetailMapper;
import com.csbaic.jd.service.IMemberCommissionService;
import com.csbaic.jd.service.IOrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csbaic.jd.service.IOrderSkuService;
import com.csbaic.jd.service.order.util.OrderUtils;
import com.csbaic.common.result.ResultCode;
import com.csbaic.jd.service.settlement.BigDecimalPrecisionStrategy;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单记录 服务实现类
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-14
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetailEntity> implements IOrderDetailService {

    private static final int QUERY_TYPE_BUY = 1;

    private static final int QUERY_TYPE_REC = 2;

    //平台奖励订单
    private static final int QUERY_TYPE_PLATFORM = 3;



    private static final int DEFAULT_PAGE_INDEX = 0;
    private static final int DEFAULT_PAGE_SIZE = 20;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Autowired
    private IMemberCommissionService commissionService;

    @Autowired
    private IOrderSkuService orderSkuService;


    @Autowired
    private BigDecimalPrecisionStrategy precisionStrategy;



    @Override
    public List<Long> findUsersByFinishedOrder(LocalDateTime begin, LocalDateTime end) {
        return getBaseMapper().findUsersByFinishedOrder(begin, end);
    }

    @Override
    public int getCountWithBuyerBetween(Long buyerId, LocalDateTime begin, LocalDateTime end) {
        return count(
                Wrappers.<OrderDetailEntity>query()
                        .eq(OrderDetailEntity.BUYER_ID, buyerId)
                        .between(OrderDetailEntity.ORDER_TIME, begin, end)
        );
    }

    @Override
    public int getCountWithOwnerBetween(Long ownerId, LocalDateTime begin, LocalDateTime end) {
        return count(
                Wrappers.<OrderDetailEntity>query()
                        .eq(OrderDetailEntity.OWNER_ID, ownerId)
                        .between(OrderDetailEntity.ORDER_TIME, begin, end)
        );
    }

    @Override
    public IPage<OrderInfo> queryOrderByTime(Long userId, QueryOrder queryOrder) {
        int page = queryOrder.getPageIndex() == null ? DEFAULT_PAGE_INDEX : queryOrder.getPageIndex();
        int size = queryOrder.getPageSize() == null ? DEFAULT_PAGE_SIZE : queryOrder.getPageSize();


        if(userId == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "用户Id不能为空");
        }






        LocalDate begin = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(1);

        if(!Strings.isNullOrEmpty(queryOrder.getBegin())){
            try {
                begin = LocalDate.from(formatter.parse(queryOrder.getBegin()));
            }catch (DateTimeParseException e){
                log.error("err",e);
                throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "请使用正确的时间格式："  + formatter.toString());
            }
        }

        if(!Strings.isNullOrEmpty(queryOrder.getEnd())){
            try {
                end = LocalDate.from(formatter.parse(queryOrder.getEnd()));
            }catch (DateTimeParseException e){
                log.error("err",e);
                throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "请使用正确的时间格式："  + formatter.toString());
            }
        }

        QueryWrapper<OrderDetailEntity> queryWrapper =
                Wrappers.<OrderDetailEntity>query()
                .between(OrderDetailEntity.ORDER_TIME, begin.atStartOfDay(),LocalDateTime.of(end, LocalTime.MAX))
                .orderByDesc(OrderDetailEntity.ORDER_TIME);

        //判断是我的订单还是我推广的订单
        if(Objects.equals(queryOrder.getType() , QUERY_TYPE_REC) ){
            queryWrapper.eq(OrderDetailEntity.OWNER_ID, userId);
        }else{
            queryWrapper.eq(OrderDetailEntity.BUYER_ID, userId);
        }

        //映射订单状态，所有订单不需要过滤订单状态
        if(queryOrder.getStatus() != null && OrderStatus.statusOf(queryOrder.getStatus()) != null){
            OrderStatus status=  OrderStatus.statusOf(queryOrder.getStatus());
            
            switch (status){

                case WAIT_FOR_PAY:
                    queryWrapper.eq(OrderDetailEntity.VALID_CODE, OrderValidCode.WAIT_FOR_PAY.getCode());
                    break;
                case PAID:
                    queryWrapper.eq(OrderDetailEntity.VALID_CODE, OrderValidCode.PAID.getCode());
                    break;
                case FINISHED:
                    queryWrapper.eq(OrderDetailEntity.VALID_CODE, OrderValidCode.FINISHED.getCode());
                    break;
                case INVALID_ORDER:
                    queryWrapper.notIn(
                            OrderDetailEntity.VALID_CODE,
                            OrderValidCode.UNKNOWN.getCode(),
                            OrderValidCode.WAIT_FOR_PAY.getCode(),
                            OrderValidCode.PAID.getCode(),
                            OrderValidCode.FINISHED.getCode(),
                            OrderValidCode.PAID.getCode(),
                            OrderValidCode.SETTLED.getCode()
                    );

                    break;
                case CANCELED:
                    queryWrapper.eq(OrderDetailEntity.VALID_CODE, OrderValidCode.INVALID_CANCEL.getCode());
                    break;
            }
        }


        IPage<OrderDetailEntity> orderDetailEntityIPage = page(new Page<>(page, size), queryWrapper);
        return orderDetailEntityIPage.convert(new Function<OrderDetailEntity, OrderInfo>() {
            @Nullable
            @Override
            public OrderInfo apply(@Nullable OrderDetailEntity input) {
                return mapOrder(input, queryOrder.getType());
            }
        });
    }


    public  OrderInfo mapOrder(OrderDetailEntity entity, int queryType){
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(entity, orderInfo);
        orderInfo.setValidCodeDesc(OrderValidCode.valueOfCode(entity.getValidCode()).getDesc());
        orderInfo.setPlus(Objects.equals(entity.getPlus(), 1));

        QueryWrapper<OrderSkuEntity> skuQuery = Wrappers.<OrderSkuEntity> query()
                .eq(OrderSkuEntity.ORDER_ID, entity.getOrderId());

        List<OrderSkuEntity> skuEntities = orderSkuService.list(skuQuery);
        List<OrderSkuInfo> skuInfos = skuEntities.stream()
                .map((e) -> mapSku(e, queryType))
                .collect(Collectors.toList());

        BigDecimal estimateRebateFee = BigDecimal.ZERO;
         estimateRebateFee = skuInfos.stream()
                .filter(Objects::nonNull)
                .map((o) -> o.getEstimateRebateFee())
                .reduce(estimateRebateFee, (bigDecimal, bigDecimal2) -> bigDecimal.add(bigDecimal2));

        BigDecimal estimateCommissionFee = BigDecimal.ZERO;
        estimateCommissionFee = skuInfos.stream()
                .filter(Objects::nonNull)
                .map((o) -> o.getEstimateCommissionFee())
                .reduce(estimateCommissionFee, (bigDecimal, bigDecimal2) -> bigDecimal.add(bigDecimal2));

        orderInfo.setEstimateCommissionFee(precisionStrategy.apply(estimateCommissionFee));
        orderInfo.setEstimateRebateFee(precisionStrategy.apply(estimateRebateFee));
        orderInfo.setSkuInfo(skuInfos);

        return orderInfo;
    }

    public OrderSkuInfo mapSku(OrderSkuEntity orderSkuEntity, int queryType){
        OrderSkuInfo orderSkuInfo = new OrderSkuInfo();
        BeanUtils.copyProperties(orderSkuEntity, orderSkuInfo);
        Long userId = null;

        if(queryType == QUERY_TYPE_BUY){
            userId = orderSkuEntity.getBuyerId();
        }else if(queryType == QUERY_TYPE_REC){
            userId = orderSkuEntity.getOwnerId();
        }

        MergedMemberCommission memberCommissionInfo = commissionService.getMergedOrderSkuFee(userId, orderSkuEntity.getOrderId(), orderSkuEntity.getSkuId());
        orderSkuInfo.setEstimateCommissionFee(precisionStrategy.apply(memberCommissionInfo.getEstimateCommissionFee()));
        orderSkuInfo.setEstimateRebateFee(precisionStrategy.apply(memberCommissionInfo.getEstimateRebateFee()));
        orderSkuInfo.setValidCodeDesc(OrderValidCode.valueOfCode(orderSkuEntity.getValidCode()).getDesc());

        if(Objects.equals(orderSkuEntity.getTraceType(), 2)){
            orderSkuInfo.setTraceType("同店");
        }else  if(Objects.equals(orderSkuEntity.getTraceType(), 3)){
            orderSkuInfo.setTraceType("跨店");
        }

        orderSkuInfo.setTag(OrderUtils.resolveUnionTag(orderSkuEntity.getUnionTag()));

        return orderSkuInfo;
    }

}
