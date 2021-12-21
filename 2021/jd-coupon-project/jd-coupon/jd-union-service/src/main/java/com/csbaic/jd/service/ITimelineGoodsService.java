package com.csbaic.jd.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.dto.CreateTimelineGoods;
import com.csbaic.jd.dto.TimelineGoods;
import com.csbaic.jd.entity.TimelineGoodsEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 发圈必备 服务类
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-14
 */
public interface ITimelineGoodsService extends IService<TimelineGoodsEntity> {

    /**
     * 获取发圈商品
     * @param pageIndex
     * @param pageSize
     * @return
     */
    IPage<TimelineGoods> getTimelineGoods(Integer pageIndex, Integer pageSize);


    /**
     * 创建发圈商品
     * @param goods
     */
    void createTimelineGoods(CreateTimelineGoods goods);
}
