package com.csbaic.jd.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csbaic.jd.dto.*;
import com.csbaic.jd.entity.TimelineGoodsEntity;
import com.csbaic.jd.entity.UserEntity;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.jd.mapper.TimelineGoodsMapper;
import com.csbaic.jd.service.GoodsService;
import com.csbaic.jd.service.ITimelineGoodsService;
import com.csbaic.jd.service.IUserService;
import com.csbaic.common.result.ResultCode;
import com.google.common.base.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimelineGoodsServiceImpl extends ServiceImpl<TimelineGoodsMapper, TimelineGoodsEntity> implements ITimelineGoodsService {


    @Autowired
    private IUserService userService;


    @Autowired
    private GoodsService goodsService;


    @Override
    public IPage<TimelineGoods> getTimelineGoods(Integer pageIndex, Integer pageSize) {
        int page = pageIndex == null ? 1 : pageIndex;
        int size = pageSize == null  ? 20 : pageSize;

        return page(new Page<>(page, size))
                .convert(this::map);
    }

    @Override
    public void createTimelineGoods(CreateTimelineGoods goods) {
        if(goods == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "参数错误");
        }

        if(Strings.isNullOrEmpty(goods.getTitle())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "标题不能为空");
        }


        if(Strings.isNullOrEmpty(goods.getContent())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "内容不能为空");
        }


        if(Strings.isNullOrEmpty(goods.getImageUrl())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "图片不能为空");
        }


        if(goods.getSkuId() != null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "商品sku不能为空");
        }

        List<Goods> promotionGoodsList = goodsService.queryGoodsDetail(String.valueOf(goods.getSkuId()));
        if(promotionGoodsList.isEmpty()){
            throw BizRuntimeException.from(ResultCode.NOT_FOUND, "未找到商品");
        }

//        PromotionGoods goodsDetail = promotionGoodsList.find(0);
//        TimelineGoodsEntity timelineGoods = new TimelineGoodsEntity();
//        BeanUtils.copyProperties(goods, timelineGoods);
//        timelineGoods.setImageUrl(goods.getImageUrl());



    }


    public   TimelineGoods map(TimelineGoodsEntity entity){
        TimelineGoods goods = new TimelineGoods();
        BeanUtils.copyProperties(entity, goods);


        UserEntity userEntity = userService.getById(entity.getId());

        if(userEntity == null){
            throw BizRuntimeException.from(ResultCode.NOT_FOUND, "未找到发布人");
        }

        PublisherInfo publisherInfo = new PublisherInfo();
        publisherInfo.setId(userEntity.getId());
        publisherInfo.setAvatarUrl(userEntity.getAvatarUrl());
        publisherInfo.setNickName(userEntity.getNickName());

        goods.setPublisherInfo(publisherInfo);
        return goods;
    }


}
