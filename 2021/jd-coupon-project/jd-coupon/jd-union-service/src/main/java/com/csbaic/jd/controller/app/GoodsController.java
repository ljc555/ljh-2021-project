package com.csbaic.jd.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.dto.*;
import com.csbaic.jd.service.GoodsService;
import com.csbaic.jd.service.ITimelineGoodsService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商品接口", tags = "商品接口")
@RequestMapping("/goods")
@RestController
@ResponseResult
public class GoodsController {

    @Autowired
    private GoodsService goodsService;



    @ApiOperation("热门商品")
    @PostMapping("/hot")
    public IPage<Goods> queryHotGoods(@RequestBody PageQuery query){

        GoodsQuery goodsQuery = new GoodsQuery();
//        goodsQuery.setOwner("g");
        goodsQuery.setPageIndex(query.getPageIndex());
        goodsQuery.setPageSize(query.getPageSize());
        goodsQuery.setIsHot(1);
        return goodsService.queryGoods(goodsQuery);
    }


    @ApiOperation("自营商品（京东发货）")
    @PostMapping("/deliver_by_jd")
    public IPage<Goods> queryGoodsForJdDeliver(@RequestBody PageQuery query){
        GoodsQuery goodsQuery = new GoodsQuery();
        goodsQuery.setPageIndex(query.getPageIndex());
        goodsQuery.setPageSize(query.getPageSize());
        goodsQuery.setOwner("g");
        return goodsService.queryGoods(goodsQuery);
    }


    @ApiOperation("王牌商品（品牌专区）")
    @PostMapping("/brand")
    public IPage<Goods> queryGoodsForBrand(@RequestBody PageQuery query){
        JFGoodsQuery jfGoodsQuery = new JFGoodsQuery();
        jfGoodsQuery.setPageIndex(query.getPageIndex());
        jfGoodsQuery.setPageSize(query.getPageSize());
        jfGoodsQuery.setEliteId(32);

        return goodsService.queryJFGoods(jfGoodsQuery);
    }


    @ApiOperation("好券商品（优惠券）")
    @PostMapping("/coupons")
    public IPage<Goods> queryGoodsForCoupon(@RequestBody PageQuery query){

        JFGoodsQuery jfGoodsQuery = new JFGoodsQuery();
        jfGoodsQuery.setPageIndex(query.getPageIndex());
        jfGoodsQuery.setPageSize(query.getPageSize());
        jfGoodsQuery.setEliteId(1);

        return goodsService.queryJFGoods(jfGoodsQuery);
    }


    @ApiOperation("今日必推（发圈必备）")
    @PostMapping("/today")
    public IPage<Goods> queryGoodsForToday(@RequestBody PageQuery query){
        JFGoodsQuery jfGoodsQuery = new JFGoodsQuery();
        jfGoodsQuery.setPageIndex(query.getPageIndex());
        jfGoodsQuery.setPageSize(query.getPageSize());
        jfGoodsQuery.setEliteId(31);

        return goodsService.queryJFGoods(jfGoodsQuery);
    }




    @ApiOperation("搜罗全球好货")
    @PostMapping("/global")
    public IPage<Goods> queryGoodsForGlobal(@RequestBody PageQuery query){
        GoodsQuery goodsQuery = new GoodsQuery();
        goodsQuery.setPageIndex(query.getPageIndex());
        goodsQuery.setPageSize(query.getPageSize());
        return goodsService.queryGoods(goodsQuery);
    }



    @ApiOperation("专属高佣商品")
    @PostMapping("/high_commission")
    public IPage<Goods> queryGoodsForCommission(@RequestBody PageQuery query){
        GoodsQuery goodsQuery = new GoodsQuery();
        goodsQuery.setPageIndex(query.getPageIndex());
        goodsQuery.setPageSize(query.getPageSize());
        return goodsService.queryGoods(goodsQuery);
    }


    @ApiOperation("热销爆品（爆品推荐）")
    @PostMapping("/top")
    public IPage<Goods> queryGoodsForTop(@RequestBody PageQuery query){
        JFGoodsQuery jfGoodsQuery = new JFGoodsQuery();
        jfGoodsQuery.setPageIndex(query.getPageIndex());
        jfGoodsQuery.setPageSize(query.getPageSize());
        jfGoodsQuery.setEliteId(22);

        return goodsService.queryJFGoods(jfGoodsQuery);
    }


    @ApiOperation("超市（居家必备）")
    @PostMapping("/home_ownership")
    public IPage<Goods> queryGoodsForHome(@RequestBody PageQuery query){
        JFGoodsQuery jfGoodsQuery = new JFGoodsQuery();
        jfGoodsQuery.setPageIndex(query.getPageIndex());
        jfGoodsQuery.setPageSize(query.getPageSize());
        jfGoodsQuery.setEliteId(25);
        return goodsService.queryJFGoods(jfGoodsQuery);
    }

    @ApiOperation("按分类查找商品")
    @PostMapping("/by_category")
    public IPage<Goods> queryGoodsByCategory(@RequestBody QueryByCategory query){
        GoodsQuery goodsQuery = new GoodsQuery();
        goodsQuery.setPageIndex(query.getPageIndex());
        goodsQuery.setPageSize(query.getPageSize());
        goodsQuery.setCid1(query.getCid1());
        goodsQuery.setCid2(query.getCid2());
        goodsQuery.setCid3(query.getCid3());
        return goodsService.queryGoods(goodsQuery);
    }


    @ApiOperation("好评之王")
    @PostMapping("/good_comment")
    public IPage<Goods> queryGoodsForComment(@RequestBody PageQuery query){
        JFGoodsQuery jfGoodsQuery = new JFGoodsQuery();
        jfGoodsQuery.setPageIndex(query.getPageIndex());
        jfGoodsQuery.setPageSize(query.getPageSize());
        jfGoodsQuery.setEliteId(22);
        return goodsService.queryJFGoods(jfGoodsQuery);
    }

    @ApiOperation("爆款9块9")
    @PostMapping("/hot_low_price")
    public IPage<Goods> queryGoodsForLowPrice(@RequestBody PageQuery query){
        JFGoodsQuery jfGoodsQuery = new JFGoodsQuery();
        jfGoodsQuery.setPageIndex(query.getPageIndex());
        jfGoodsQuery.setPageSize(query.getPageSize());
        jfGoodsQuery.setEliteId(10);
        return goodsService.queryJFGoods(jfGoodsQuery);
    }

    @ApiOperation("2小时榜")
    @PostMapping("/two_hours_rank")
    public IPage<Goods> queryGoods(@RequestBody PageQuery query){
        JFGoodsQuery jfGoodsQuery = new JFGoodsQuery();
        jfGoodsQuery.setPageIndex(query.getPageIndex());
        jfGoodsQuery.setPageSize(query.getPageSize());
        jfGoodsQuery.setEliteId(10);
        return goodsService.queryJFGoods(jfGoodsQuery);
    }

    @ApiOperation("关键查询商品")
    @PostMapping("/")
    public IPage<Goods> queryGoodsForKeyword(@RequestBody GoodsQuery query){
        return goodsService.queryGoods(query);
    }


    @ApiOperation("查询秒杀商品")
    @PostMapping("/query_for_sec_kill")
    public IPage<SecKillGoods> querySecKillGoods(@RequestBody SecKillGoodsQuery query){
        return goodsService.querySecKillGoods(query);
    }

    @ApiOperation("查询学生价商品")
    @PostMapping("/query_for_student")
    public IPage<StuGoods> queryStudentGoods(@RequestBody StuGoodsQuery query){
        return goodsService.queryStuGoods(query);
    }

    @ApiOperation("查询商品详情")
    @GetMapping("/query_goods_detail")
    public List<Goods> queryGoodsDetail(@RequestParam("skuIds") String skuIds){
        return goodsService.queryGoodsDetail(skuIds);
    }


//    @ApiOperation("通过subUnionId获取商品推广链接")
//    @PostMapping("/get_goods_url")
//    public GoodsRecUrl getRecUrlBySubUnionId(@RequestBody GetRecUrl params){
//        return goodsService.getRecUrlBySubUnionId(params);
//    }


}
