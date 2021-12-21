package com.csbaic.jd.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.dto.*;

import java.util.List;

public interface GoodsService {


    /**
     * 商品查询
     * @param query 查询参数
     * @return 商品列表
     */
    IPage<Goods> queryGoods(GoodsQuery query);


    /**
     * 查询京粉精选商品
     * @param query 查询参数
     * @return 商品列表
     */
    IPage<Goods> queryJFGoods(JFGoodsQuery query);


    /**
     * 查询学生价商品
     * @return
     */
    IPage<StuGoods> queryStuGoods(StuGoodsQuery query);

    /**
     * 查询秒杀商品
     * @param query
     * @return
     */
    IPage<SecKillGoods> querySecKillGoods(SecKillGoodsQuery query);

    /**
     * 通过subUnionId获取推广链接
     * @param params
     * @return
     */
    GoodsRecUrl getRecUrlBySubUnionId(GetRecUrl params);


    /**
     * 查询推广商品详情
     * @param skuIds 京东skuID串，逗号分割，最多100个，
     *               开发示例如param_json={'skuIds':'5225346,7275691'}
     *               （非常重要 请大家关注：如果输入的sk串中某个skuID的商品不在推广中[就是没有佣金]，返回结果中不会包含这个商品的信息）
     */
    List<Goods> queryGoodsDetail(String skuIds);


    /**
     * 查询商品分类
     * @param pid
     * @param grade
     * @return 分类列表
     */
    List<GoodsCategory> getCategories(Integer pid, Integer grade);


    /**
     * 查询商品订单
     * @param query
     * @return
     */
    IPage<GoodsOrder> queryGoodsOrder(GoodsOrderQuery query);



}
