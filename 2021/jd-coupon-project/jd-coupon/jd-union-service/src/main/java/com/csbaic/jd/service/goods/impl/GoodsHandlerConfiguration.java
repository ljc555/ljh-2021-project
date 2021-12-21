package com.csbaic.jd.service.goods.impl;

import com.csbaic.jd.service.IUserService;
import com.csbaic.jd.service.goods.GoodsHandler;
import com.csbaic.jd.service.settlement.BigDecimalPrecisionStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class GoodsHandlerConfiguration {


    //fixme 使用Spring切面编程
    @Bean
    public GoodsHandler goodsHandler(IUserService userService, BigDecimalPrecisionStrategy precisionStrategy){
        List<GoodsHandler> goodsHandlers = new ArrayList<>();
        goodsHandlers.add(new HttpsGoodsImageUrlHandler(userService));
        goodsHandlers.add(new AdjustRebateGoodsHandler(userService, precisionStrategy));
//        goodsHandlers.add(new AdjustRebateSeckillGoodsHandler(userService));
//        goodsHandlers.add(new AdjustRebateStuGoodsHandler(userService));
        return new GoodsHandlerComposite(goodsHandlers);
    }
}
