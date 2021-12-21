package com.csbaic.jd.service.order.handle.filter;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.csbaic.jd.dto.GoodsOrder;
import com.csbaic.jd.dto.SkuInfo;
import com.csbaic.jd.entity.OrderDetailEntity;
import com.csbaic.jd.entity.OrderSkuEntity;
import com.csbaic.jd.service.IOrderDetailService;
import com.csbaic.jd.service.IOrderSkuService;
import com.csbaic.jd.service.goods.detail.GoodsDetail;
import com.csbaic.jd.service.goods.detail.GoodsDetailRepository;
import com.csbaic.jd.service.order.SubUnionId;
import com.csbaic.jd.service.order.SubUnionIdConverter;
import com.csbaic.jd.service.order.handle.OrderHandlerChain;
import com.csbaic.jd.service.order.handle.OrderMetadata;
import com.csbaic.jd.utils.TimeUtils;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by yjwfn on 2020/2/15.
 */
@Slf4j
@Component
@Order(FilterOrder.ORDER_SAVE_FILTER)
public class OrderSaveFilter implements OrderHandlerChain.OrderFilter {

    /**
     * 订单详情服务
     */
    private final IOrderDetailService orderDetailService;

    /**
     * 订单sku服务
     */
    private final IOrderSkuService orderSkuService;

    private final SubUnionIdConverter subUnionIdConverter;

    private final GoodsDetailRepository goodsDetailRepository;

    @Autowired
    public OrderSaveFilter(IOrderDetailService orderDetailService, IOrderSkuService orderSkuService, SubUnionIdConverter subUnionIdResolver, GoodsDetailRepository goodsDetailRepository) {
        this.orderDetailService = orderDetailService;
        this.orderSkuService = orderSkuService;
        this.subUnionIdConverter = subUnionIdResolver;
        this.goodsDetailRepository = goodsDetailRepository;
    }

    @Transactional
    @Override
    public void filter(OrderHandlerChain chain) {

        OrderMetadata orderMetadata = chain.getOrderMetadata();
        log.info("Save order begin: {}", orderMetadata.getOrderId());

        /*
            只处理GoodsOrder
         */
        if(!(orderMetadata instanceof GoodsOrder)){
            log.info("skip save");

            chain.handle(orderMetadata);
            return;
        }

        GoodsOrder goodsOrder  = (GoodsOrder) orderMetadata;
        OrderDetailEntity orderDetailEntity = orderDetailService.getOne(
                Wrappers.<OrderDetailEntity>query()
                        .eq(OrderDetailEntity.ORDER_ID, goodsOrder.getOrderId())
        );

        if(orderDetailEntity == null){
            orderDetailEntity = new OrderDetailEntity();
        }

        /*
            更新或保存订单
         */
        BeanUtils.copyProperties(goodsOrder, orderDetailEntity);
        Long orderTime = orderMetadata.getOrderTime();
        Long finishTime = orderMetadata.getFinishTime();


        orderDetailEntity.setOrderTime(orderTime != null && orderTime > 0 ? TimeUtils.toLocalDateTime(orderTime) : null);
        orderDetailEntity.setFinishTime(finishTime != null && finishTime > 0 ? TimeUtils.toLocalDateTime(finishTime) : null);




        /*
            获取商品详情信息
         */
        final SkuInfo[] skus = goodsOrder.getSkuList();
        Map<Long, GoodsDetail> detailMap = new HashMap<>();

        //通过sku获取商品详情
        if (skus.length > 0) {
            Long[] ids = Arrays.stream(skus)
                    .map(SkuInfo::getSkuId)
                    .toArray(value -> new Long[skus.length]);

            //商品详情
            detailMap = goodsDetailRepository.getGoodsDetail(ids)
                    .stream()
                    .collect(Collectors.toMap(GoodsDetail::getSkuId, goodsDetail -> goodsDetail));

        }


        String skuImage = "";
        List<OrderSkuEntity> skuEntityList = new ArrayList<>();
        for(SkuInfo skuInfo : skus){
            //查找已经存在的订单
            OrderSkuEntity skuEntity = orderSkuService.getOne(
                    Wrappers.<OrderSkuEntity>query()
                    .eq(OrderSkuEntity.ORDER_ID, goodsOrder.getOrderId())
                    .eq(OrderSkuEntity.SKU_ID, skuInfo.getSkuId())
            );

            if(skuEntity == null){
                skuEntity = new OrderSkuEntity();
            }

            SubUnionId subUnionId = subUnionIdConverter.covert(skuInfo.getSubUnionId());
            if(orderDetailEntity.getOwnerId() == null){
                orderDetailEntity.setOwnerId(subUnionId.getOwner());
            }

            if(orderDetailEntity.getBuyerId() == null){
                orderDetailEntity.setBuyerId(subUnionId.getBuyer());
            }


            GoodsDetail goodsDetail = detailMap.get(skuInfo.getSkuId());
            BeanUtils.copyProperties(skuInfo, skuEntity);
            skuEntity.setActualCosPrice(BigDecimal.valueOf(skuInfo.getActualCosPrice()));
            skuEntity.setActualFee(BigDecimal.valueOf(skuInfo.getActualFee()));
            skuEntity.setEstimateCosPrice(BigDecimal.valueOf(skuInfo.getEstimateCosPrice()));
            skuEntity.setEstimateFee(BigDecimal.valueOf(skuInfo.getEstimateFee()));
            skuEntity.setCommissionRate(BigDecimal.valueOf(skuInfo.getCommissionRate()));
            skuEntity.setOrderId(orderMetadata.getOrderId());
            skuEntity.setOwnerId(subUnionId.getOwner());
            skuEntity.setBuyerId(subUnionId.getBuyer());
            skuEntity.setSubUnionId(skuInfo.getSubUnionId());

            if(goodsDetail != null && goodsDetail.getImages() != null && !goodsDetail.getImages().isEmpty()){
                skuEntity.setSkuImage(goodsDetail.getImages().get(0));
                skuImage = skuEntity.getSkuImage();
            }

            skuEntityList.add(skuEntity);

        }

        //检查是否有没有图片的商品，有些赠品没有图片
        for(OrderSkuEntity skuEntity : skuEntityList){
            if(Strings.isNullOrEmpty(skuEntity.getSkuImage())){
                skuEntity.setSkuImage(skuImage);
            }
        }


        orderDetailService.saveOrUpdate(orderDetailEntity);
        orderSkuService.saveOrUpdateBatch(skuEntityList);

        log.info("Save order into db --> id（{}）", orderMetadata.getOrderId());
        log.info("Save finish {}", orderMetadata.getOrderId());

        /*
            继续处理
         */
        chain.handle(orderMetadata);
    }
}
