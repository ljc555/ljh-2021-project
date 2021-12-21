package com.csbaic.jd.service.goods.impl;

import com.csbaic.jd.entity.UserEntity;
import com.csbaic.jd.service.IUserService;
import com.csbaic.jd.service.goods.GoodsHandler;
import com.csbaic.jd.service.goods.GoodsMetadata;
import org.springframework.core.ResolvableType;


/**
 * 按用户调整商品
 */
public abstract class AbstractUserGoodsHandler<G extends GoodsMetadata> implements GoodsHandler {

    /**
     * 查找用户信息
     */
    private final IUserService userService;


    protected AbstractUserGoodsHandler(IUserService userService) {
        this.userService = userService;
    }


    @Override
    public final void handle(GoodsMetadata metadata) {
        if(!(metadata instanceof UserIdAndGoodsMetadata)){
            return;
        }

        GoodsMetadata goodsMetadata  = ((UserIdAndGoodsMetadata) metadata).getOrigin();

        if(!support(goodsMetadata)){
            return;
        }

        Long userId = ((UserIdAndGoodsMetadata) metadata).getUserId();
        if(requireUser() && userId == null){
            return;
        }



        UserEntity userEntity = userId != null ? userService.getById(((UserIdAndGoodsMetadata) metadata).getUserId()) : null;
        if(requireUser() && userEntity == null){
            return;
        }


        handleImpl(userEntity, (G) goodsMetadata);
    }



    /**
     * 子类处理商品信息
     * @param metadata
     */
    protected abstract void handleImpl(UserEntity userEntity, G metadata);


    /**
     * 是否支持相应的{@link GoodsMetadata}
     * @param metadata
     * @return
     */
    protected boolean support(GoodsMetadata metadata){
        Class<?> clz = ResolvableType.forClass(this.getClass()).getSuperType().resolveGeneric(0);
        return clz != null && clz.isAssignableFrom(metadata.getClass());
    }

    /**
     * 是否必须要用户
     * @return
     */
    protected boolean requireUser(){
        return false;
    }


}
