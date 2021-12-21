package com.csbaic.jd.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.dto.CreateTimelineGoods;
import com.csbaic.jd.dto.PageQuery;
import com.csbaic.jd.dto.TimelineGoods;
import com.csbaic.jd.service.ITimelineGoodsService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "朋友圈必推", tags = "朋友圈必推")
@RequestMapping("/timeline_goods")
@RestController
@ResponseResult
public class TimelineGoodsController {


    @Autowired
    private ITimelineGoodsService timelineGoodsService;



    /**
     * 查询发圈商品
     * @return
     */
    @ApiOperation("查询发圈商品")
    @PostMapping("/get_timeline_goods")
    public IPage<TimelineGoods> getTimelineGoods(@RequestBody PageQuery query){
        return timelineGoodsService.getTimelineGoods(query.getPageIndex(),query.getPageSize());
    }

    @ApiOperation("登陆发圈商品")
    @PostMapping
    public void createTimelineGoods(@RequestBody CreateTimelineGoods goods){
        timelineGoodsService.createTimelineGoods(goods);
    }


}
