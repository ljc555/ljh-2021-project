package com.csbaic.jd.controller.app;

import com.csbaic.jd.dto.Activity;
import com.csbaic.jd.service.IActivityService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activities")
@Api(value = "活动", tags = "活动")
@ResponseResult
public class ActivityController {

    @Autowired
    private IActivityService activityService;


    /**
     * 获取活动详情
     * @return banner列表
     */
    @ApiOperation("获取活动详情")
    @GetMapping("/{id}")
    public Activity getActivityById(@PathVariable("id") Long id){
        return activityService.getActivityById(id);
    }


}
