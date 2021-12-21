package com.csbaic.jd.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csbaic.common.convert.ObjectConvert;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.common.result.ResultCode;
import com.csbaic.jd.dto.*;
import com.csbaic.jd.service.GoodsService;
import com.csbaic.jd.service.goods.GoodsFilterStrategy;
import com.csbaic.jd.service.goods.GoodsHandler;
import com.csbaic.jd.service.goods.GoodsMetadata;
import com.csbaic.jd.service.goods.impl.UserIdAndGoodsMetadata;
import com.csbaic.jd.utils.UserUtils;
import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.request.JdRequest;
import com.jd.open.api.sdk.response.AbstractResponse;
import jd.union.open.category.goods.get.request.CategoryReq;
import jd.union.open.category.goods.get.request.UnionOpenCategoryGoodsGetRequest;
import jd.union.open.category.goods.get.response.CategoryResp;
import jd.union.open.category.goods.get.response.UnionOpenCategoryGoodsGetResponse;
import jd.union.open.goods.jingfen.query.request.JFGoodsReq;
import jd.union.open.goods.jingfen.query.request.UnionOpenGoodsJingfenQueryRequest;
import jd.union.open.goods.jingfen.query.response.Coupon;
import jd.union.open.goods.jingfen.query.response.JFGoodsResp;
import jd.union.open.goods.jingfen.query.response.UnionOpenGoodsJingfenQueryResponse;
import jd.union.open.goods.jingfen.query.response.UrlInfo;
import jd.union.open.goods.query.request.GoodsReq;
import jd.union.open.goods.query.request.UnionOpenGoodsQueryRequest;
import jd.union.open.goods.query.response.GoodsResp;
import jd.union.open.goods.query.response.UnionOpenGoodsQueryResponse;
import jd.union.open.goods.seckill.query.request.SecKillGoodsReq;
import jd.union.open.goods.seckill.query.request.UnionOpenGoodsSeckillQueryRequest;
import jd.union.open.goods.seckill.query.response.SecKillGoodsResp;
import jd.union.open.goods.seckill.query.response.UnionOpenGoodsSeckillQueryResponse;
import jd.union.open.goods.stuprice.query.request.StuPriceGoodsReq;
import jd.union.open.goods.stuprice.query.request.UnionOpenGoodsStupriceQueryRequest;
import jd.union.open.goods.stuprice.query.response.StuPriceGoodsResp;
import jd.union.open.goods.stuprice.query.response.UnionOpenGoodsStupriceQueryResponse;
import jd.union.open.order.query.request.OrderReq;
import jd.union.open.order.query.request.UnionOpenOrderQueryRequest;
import jd.union.open.order.query.response.OrderResp;
import jd.union.open.order.query.response.UnionOpenOrderQueryResponse;
import jd.union.open.promotion.bysubunionid.get.request.PromotionCodeReq;
import jd.union.open.promotion.bysubunionid.get.request.UnionOpenPromotionBysubunionidGetRequest;
import jd.union.open.promotion.bysubunionid.get.response.PromotionCodeResp;
import jd.union.open.promotion.bysubunionid.get.response.UnionOpenPromotionBysubunionidGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class GoodsServiceImpl implements GoodsService {

    private static final String APP_KEY = "替换成自己的应用id";
    private static final String APP_SECRET = "替换成自己的密钥";
    private static final String JD_ENDPOINT  = "https://router.jd.com/api";

    private static final int DEFAULT_PAGE_INDEX = 0;
    private static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 京东联盟客户端
     */
    private static final JdClient client = new DefaultJdClient(JD_ENDPOINT,null,APP_KEY,APP_SECRET);

    @Autowired
    private GoodsHandler goodsHandler;


    @Autowired
    private  GoodsFilterStrategy goodsFilterStrategy;

    /***
     * 关键字搜索商品
     * @param query
     * @return
     */
    public IPage<Goods> queryGoods(GoodsQuery query){

        int page = query.getPageIndex() == null ? DEFAULT_PAGE_INDEX : query.getPageIndex();
        int size = query.getPageSize() == null ? DEFAULT_PAGE_SIZE : query.getPageSize();

        UnionOpenGoodsQueryRequest request= new UnionOpenGoodsQueryRequest();
        GoodsReq goodsReqDTO = ObjectConvert.spring(GoodsReq.class).convert(query);


        goodsReqDTO.setPageIndex(query.getPageIndex());
        goodsReqDTO.setPageSize(query.getPageSize());


        String keyword = query.getKeyword();
        if(StringUtils.isNumeric(keyword)){
            goodsReqDTO.setSkuIds(new Long[]{ Long.parseLong(keyword) });
            goodsReqDTO.setKeyword(null);
        }


        request.setGoodsReqDTO(goodsReqDTO);
        UnionOpenGoodsQueryResponse response = execute(request);

        log.debug("queryGoods: {}", JSON.toJSONString(response));
        if(response == null || response.getData() == null){
            return new Page<>(page, size, -1);
        }

        List<Goods> goods = Arrays.stream(response.getData())
                .map(GoodsServiceImpl::mapGoods)
                .filter(goodsFilterStrategy::apply)
                .collect(Collectors.toList());

        //执行商品处理器
        goods.forEach(this::performGoodsHandler);

        return new Page<Goods>(page, size, response.getTotalCount()).setRecords(goods);
    }



    /***
     * 京粉精选商品查询
     * @return
     */
    public IPage<Goods> queryJFGoods(JFGoodsQuery query){
        int page = query.getPageIndex() == null ? DEFAULT_PAGE_INDEX : query.getPageIndex();
        int size = query.getPageSize() == null ? DEFAULT_PAGE_SIZE : query.getPageSize();

        UnionOpenGoodsJingfenQueryRequest request=new UnionOpenGoodsJingfenQueryRequest();
        JFGoodsReq goodsReq=new JFGoodsReq();
        BeanUtils.copyProperties(query, goodsReq);
        goodsReq.setPageIndex(query.getPageIndex());
        goodsReq.setPageSize(query.getPageSize());
//        goodsReq.setIsCoupon(query.getIsCoupon());
        request.setGoodsReq(goodsReq);
        UnionOpenGoodsJingfenQueryResponse response= execute(request);

        log.debug("queryJFGoods: {}", JSON.toJSONString(response));

        List<Goods> goods = Arrays.stream(response.getData())
                .map(GoodsServiceImpl::mapGoods)
                .filter(goodsFilterStrategy::apply)
                .collect(Collectors.toList());

        //执行商品处理器
        goods.forEach(this::performGoodsHandler);

        return new Page<Goods>(page, size, response.getTotalCount()).setRecords(goods);
    }

    @Override
    public IPage<StuGoods> queryStuGoods(StuGoodsQuery query) {
        int page = query.getPageIndex() == null ? DEFAULT_PAGE_INDEX : query.getPageIndex();
        int size = query.getPageSize() == null ? DEFAULT_PAGE_SIZE : query.getPageSize();

        UnionOpenGoodsStupriceQueryRequest request=new UnionOpenGoodsStupriceQueryRequest();
        StuPriceGoodsReq goodsReq=new StuPriceGoodsReq();
        request.setGoodsReq(goodsReq);
        UnionOpenGoodsStupriceQueryResponse response= execute(request);

        List<StuGoods> goods = Arrays.stream(response.getData())
                .map(GoodsServiceImpl::mapStuGoods)
                .filter(goodsFilterStrategy::apply)
                .collect(Collectors.toList());


        //执行商品处理器
        goods.forEach(this::performGoodsHandler);

        return new Page<StuGoods>(page, size, response.getTotalCount()).setRecords(goods);
    }

    @Override
    public Page<SecKillGoods> querySecKillGoods(SecKillGoodsQuery query) {

        int page = query.getPageIndex() == null ? DEFAULT_PAGE_INDEX : query.getPageIndex();
        int size = query.getPageSize() == null ? DEFAULT_PAGE_SIZE : query.getPageSize();

        UnionOpenGoodsSeckillQueryRequest request=new UnionOpenGoodsSeckillQueryRequest();
        SecKillGoodsReq goodsReq=new SecKillGoodsReq();
        BeanUtils.copyProperties(query, goodsReq);
        request.setGoodsReq(goodsReq);
        UnionOpenGoodsSeckillQueryResponse response = execute(request);

        SecKillGoodsResp[] goodsResps = response.getData();
        if(goodsResps == null){
            return new Page<>(page, size, 0);
        }

        List<SecKillGoods> goods = Arrays.stream(goodsResps)
                .map(GoodsServiceImpl::mapGoods)
                .filter(goodsFilterStrategy::apply)
                .collect(Collectors.toList());

        //执行商品处理器
        goods.forEach(this::performGoodsHandler);


        return new Page<SecKillGoods>(page, size, response.getTotalCount()).setRecords(goods);
    }


    @Override
    public GoodsRecUrl getRecUrlBySubUnionId(GetRecUrl params) {
        UnionOpenPromotionBysubunionidGetRequest request=new UnionOpenPromotionBysubunionidGetRequest();
        PromotionCodeReq promotionCodeReq=new PromotionCodeReq();
        promotionCodeReq.setChainType(params.getChainType());
        promotionCodeReq.setCouponUrl(params.getCouponUrl());
        promotionCodeReq.setMaterialId(params.getMaterialId());
        promotionCodeReq.setSubUnionId(params.getSubUnionId());

        request.setPromotionCodeReq(promotionCodeReq);
        UnionOpenPromotionBysubunionidGetResponse response= execute(request);

        PromotionCodeResp promotionCodeResp = response.getData();

        if(promotionCodeResp == null){
            throw BizRuntimeException.from(ResultCode.ERROR, response.getMessage());
        }

        GoodsRecUrl goodsRecUrl = new GoodsRecUrl();
        goodsRecUrl.setClickURL(promotionCodeResp.getClickURL());
        goodsRecUrl.setShortURL(promotionCodeResp.getShortURL());

        return goodsRecUrl;
    }



    @Override
    public List<Goods> queryGoodsDetail(String skuIds) {
        if(Strings.isNullOrEmpty(skuIds)){
            throw new IllegalStateException("参数错误");
        }


        Long[] skuArray = StreamSupport.stream(
                Splitter.on(",").omitEmptyStrings().split(skuIds).spliterator(),
                false
        ).map(Long::parseLong)
                .toArray(Long[]::new);


        UnionOpenGoodsQueryRequest request= new UnionOpenGoodsQueryRequest();
        GoodsReq goodsReqDTO= new GoodsReq();
        goodsReqDTO.setSkuIds(skuArray);
        request.setGoodsReqDTO(goodsReqDTO);
        UnionOpenGoodsQueryResponse response = execute(request);

        if(response == null || response.getData() == null){
            return new ArrayList<>();
        }

        List<Goods> goods = Arrays.stream(response.getData())
                .map(GoodsServiceImpl::mapGoods)
                .filter(goodsFilterStrategy::apply)
                .collect(Collectors.toList());

        //执行商品处理器
        goods.forEach(this::performGoodsHandler);

        return goods;
    }

    @Override
    public List<GoodsCategory> getCategories(Integer pid, Integer grade) {
        UnionOpenCategoryGoodsGetRequest request=new UnionOpenCategoryGoodsGetRequest();
        CategoryReq req=new CategoryReq();
        req.setGrade(grade);
        req.setParentId(pid);
        request.setReq(req);
        UnionOpenCategoryGoodsGetResponse response= execute(request);

        if(response.getData() != null){
            return Arrays.stream(response.getData())
                    .filter(Objects::nonNull)
                    .map(new Function<CategoryResp, GoodsCategory>() {
                        @Nullable
                        @Override
                        public GoodsCategory apply(@Nullable CategoryResp input) {
                            GoodsCategory category = new GoodsCategory();
                            BeanUtils.copyProperties(input, category);
                            return category;
                        }
                    }).collect(Collectors.toList());
        }


        return new ArrayList<>();
    }


    @Override
    public IPage<GoodsOrder> queryGoodsOrder(GoodsOrderQuery query) {
        int page = query.getPageIndex() == null ? DEFAULT_PAGE_INDEX : query.getPageIndex();
        int size = query.getPageSize() == null ? DEFAULT_PAGE_SIZE : query.getPageSize();


        UnionOpenOrderQueryRequest request=new UnionOpenOrderQueryRequest();
        OrderReq orderReq= new OrderReq();
        BeanUtils.copyProperties(query, orderReq);
        orderReq.setPageNo(page);
        request.setOrderReq(orderReq);
        UnionOpenOrderQueryResponse response = execute(request);

        OrderResp[] orderResps = response.getData();
        if(orderResps != null){
            List<GoodsOrder> data =  Arrays.stream(orderResps)
                    .filter(Objects::nonNull)
                    .map(new Function<OrderResp, GoodsOrder>() {
                        @Nullable
                        @Override
                        public GoodsOrder apply(@Nullable OrderResp input) {
                            GoodsOrder goodsOrder = new GoodsOrder();
                            BeanUtils.copyProperties(input, goodsOrder);

                            List<SkuInfo> skuInfos = Arrays.stream(input.getSkuList())
                                    .map(new Function<jd.union.open.order.query.response.SkuInfo, SkuInfo>() {
                                        @Nullable
                                        @Override
                                        public SkuInfo apply(@Nullable jd.union.open.order.query.response.SkuInfo input) {
                                            SkuInfo skuInfo = new SkuInfo();
                                            BeanUtils.copyProperties(input, skuInfo);
                                            return skuInfo;
                                        }
                                    }).collect(Collectors.toList());

                            goodsOrder.setSkuList(skuInfos.toArray(new SkuInfo[]{}));

                            return goodsOrder;
                        }
                    }).collect(Collectors.toList());

           return new Page<GoodsOrder>(page, size, -1).setRecords(data);
        }

        return new Page<GoodsOrder>(page, size, 0);
    }

    /**
     * 商品实体转换
     * @param goodsResp
     * @return
     */
    public static Goods mapGoods(GoodsResp goodsResp){
        Goods goods  = new Goods();
        goods.setSkuId(goodsResp.getSkuId());
        goods.setSkuName(goodsResp.getSkuName());
        goods.setMaterialUrl(goodsResp.getMaterialUrl());
        goods.setComments(goodsResp.getComments());
        goods.setGoodCommentsShare(goodsResp.getGoodCommentsShare());
        goods.setIsHot(goodsResp.getIsHot());
        goods.setInOrderCount30Days(goodsResp.getInOrderCount30Days());

        //价格信息
        PriceInfo priceInfo = new PriceInfo();
        BeanUtils.copyProperties(goodsResp.getPriceInfo(), priceInfo);
        goods.setPriceInfo(priceInfo);


        //拼购
        PinGouInfo pinGouInfo = new PinGouInfo();
        BeanUtils.copyProperties(goodsResp.getPinGouInfo(), pinGouInfo);
        goods.setPinGouInfo(pinGouInfo);

//        //秒杀
//        SeckillInfo seckillInfo = new SeckillInfo();
//        BeanUtils.copyProperties(goodsResp.getSeckillInfo(), seckillInfo);
//        goods.setSeckillInfo(seckillInfo);
//
//        //资源
//        ResourceInfo resourceInfo  = new ResourceInfo();
//        BeanUtils.copyProperties(goodsResp.getResourceInfo(), resourceInfo);
//        goods.setResourceInfo(resourceInfo);

        //图片信息
        jd.union.open.goods.query.response.UrlInfo[] urlInfos = goodsResp.getImageInfo().getImageList();
        if(urlInfos != null){

            List<String> images = new ArrayList<>();
            for(jd.union.open.goods.query.response.UrlInfo info : urlInfos){
                images.add(info.getUrl());
            }

            goods.setImages(images);
        }

        //类别信息
        CategoryInfo categoryInfo = new CategoryInfo();
        BeanUtils.copyProperties(goodsResp.getCategoryInfo(), categoryInfo);
        goods.setCategoryInfo(categoryInfo);


        //佣金信息
        CommissionInfo commissionInfo = new CommissionInfo();
        BeanUtils.copyProperties(goodsResp.getCommissionInfo(), commissionInfo);
        goods.setCommissionInfo(commissionInfo);

        //店铺信息
        ShopInfo shopInfo = new ShopInfo();
        BeanUtils.copyProperties(goodsResp.getShopInfo(), shopInfo);
        goods.setShopInfo(shopInfo);


        List<com.csbaic.jd.dto.Coupon> coupons = new ArrayList<>();

        if(goodsResp.getCouponInfo() != null){
            for(jd.union.open.goods.query.response.Coupon coupon : goodsResp.getCouponInfo().getCouponList()){
                com.csbaic.jd.dto.Coupon covertCoupon = new com.csbaic.jd.dto.Coupon();
                BeanUtils.copyProperties(coupon, covertCoupon);
                coupons.add(covertCoupon);
            }
        }

        goods.setCoupons(coupons);
        return goods;


    }

    /**
     * 商品实体转换
     * @param goodsResp
     * @return
     */
    public static SecKillGoods mapGoods(SecKillGoodsResp goodsResp){
        SecKillGoods goods  = new SecKillGoods();
        BeanUtils.copyProperties(goodsResp, goods);


        return goods;
    }

    /**
     * 商品实体转换
     * @param goodsResp
     * @return
     */
    public static Goods mapGoods(JFGoodsResp goodsResp){
        Goods goods  = new Goods();
        goods.setSkuId(goodsResp.getSkuId());
        goods.setSkuName(goodsResp.getSkuName());
        goods.setMaterialUrl(goodsResp.getMaterialUrl());
        goods.setComments(goodsResp.getComments());
        goods.setGoodCommentsShare(goodsResp.getGoodCommentsShare());
        goods.setIsHot(goodsResp.getIsHot());
        goods.setInOrderCount30DaysSku(goodsResp.getInOrderCount30DaysSku());

        //价格信息
        PriceInfo priceInfo = new PriceInfo();
        BeanUtils.copyProperties(goodsResp.getPriceInfo(), priceInfo);
        goods.setPriceInfo(priceInfo);

        //拼购
        PinGouInfo pinGouInfo = new PinGouInfo();
        BeanUtils.copyProperties(goodsResp.getPinGouInfo(), pinGouInfo);
        goods.setPinGouInfo(pinGouInfo);

        //秒杀
        SeckillInfo seckillInfo = new SeckillInfo();
        if(goodsResp.getSeckillInfo() != null){
            BeanUtils.copyProperties(goodsResp.getSeckillInfo(), seckillInfo);
            goods.setSeckillInfo(seckillInfo);
        }


        //资源
        ResourceInfo resourceInfo  = new ResourceInfo();
        BeanUtils.copyProperties(goodsResp.getResourceInfo(), resourceInfo);
        goods.setResourceInfo(resourceInfo);

        //类别信息
        CategoryInfo categoryInfo = new CategoryInfo();
        BeanUtils.copyProperties(goodsResp.getCategoryInfo(), categoryInfo);
        goods.setCategoryInfo(categoryInfo);

        //佣金信息
        CommissionInfo commissionInfo = new CommissionInfo();
        BeanUtils.copyProperties(goodsResp.getCommissionInfo(), commissionInfo);
        goods.setCommissionInfo(commissionInfo);

        //店铺信息
        ShopInfo shopInfo = new ShopInfo();
        BeanUtils.copyProperties(goodsResp.getShopInfo(), shopInfo);
        goods.setShopInfo(shopInfo);

        UrlInfo[] urlInfos = goodsResp.getImageInfo().getImageList();
        if(urlInfos != null){

            List<String> images = new ArrayList<>();
            for(UrlInfo info : urlInfos){
                images.add(info.getUrl());
            }

            goods.setImages(images);
        }

        List<com.csbaic.jd.dto.Coupon> coupons = new ArrayList<>();

        if(goodsResp.getCouponInfo() != null){
            for(Coupon coupon : goodsResp.getCouponInfo().getCouponList()){
                com.csbaic.jd.dto.Coupon covertCoupon = new com.csbaic.jd.dto.Coupon();
                BeanUtils.copyProperties(coupon, covertCoupon);
                coupons.add(covertCoupon);
            }
        }

        goods.setCoupons(coupons);
        return goods;
    }

    private static StuGoods mapStuGoods(StuPriceGoodsResp stuPriceGoodsResp){
        StuGoods stuGoods = new StuGoods();
        BeanUtils.copyProperties(stuPriceGoodsResp, stuGoods);
        return stuGoods;
    }

    /***
     * 执行
     * @param metadata
     */
    public void performGoodsHandler(GoodsMetadata metadata){
        Long userId = UserUtils.getUserId();
        goodsHandler.handle(new UserIdAndGoodsMetadata(userId, metadata));
    }

    /***
     * 获取JD响应
     * @param params
     * @param <T>
     * @return
     */
    private static <T extends AbstractResponse> T execute(JdRequest<T> params) {
        try {
            return client.execute(params);
        } catch (JdException e) {
            throw new BizRuntimeException(e);
        }
    }

}
