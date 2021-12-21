package com.csbaic.jd.service.goods.detail;

import com.csbaic.jd.dto.Goods;
import com.csbaic.jd.dto.PromotionGoods;
import com.csbaic.jd.service.GoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JDGoodsDetailRepository implements GoodsDetailRepository {

    private final GoodsService goodsService;

    public JDGoodsDetailRepository(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Override
    public List<GoodsDetail> getGoodsDetail(Long... skuIds) {
        if(skuIds == null || skuIds.length == 0){
            return new ArrayList<>();
        }

        String skuIdsAsString = Arrays.stream(skuIds)
                .map(String::valueOf)
                .collect(Collectors.joining(","));


        List<Goods> promotionGoods = goodsService.queryGoodsDetail(skuIdsAsString);
        if(promotionGoods == null || promotionGoods.isEmpty()){
            return new ArrayList<>();
        }

        return promotionGoods.stream().map(JDGoodsDetailRepository::convert).collect(Collectors.toList());
    }

    private static GoodsDetail convert(Goods goods){
        GoodsDetail detail = new GoodsDetail();
        BeanUtils.copyProperties(goods, detail);
        return detail;
    }
}
