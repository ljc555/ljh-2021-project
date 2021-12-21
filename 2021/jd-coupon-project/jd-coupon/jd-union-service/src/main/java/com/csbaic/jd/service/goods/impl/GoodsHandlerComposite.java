package com.csbaic.jd.service.goods.impl;

import com.csbaic.jd.service.goods.GoodsHandler;
import com.csbaic.jd.service.goods.GoodsMetadata;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class GoodsHandlerComposite implements GoodsHandler
{

    public final List<GoodsHandler> handlers ;


    public GoodsHandlerComposite(List<GoodsHandler> handlers) {
        this.handlers = handlers;
    }


    @Override
    public void handle(GoodsMetadata metadata) {

        for(GoodsHandler handler : handlers){
            log.info("Execute goods handle {}", handler.getClass().getName());
            handler.handle(metadata);
        }
    }


}
