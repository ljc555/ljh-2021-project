package com.csbaic.jd.controller.admin;

import com.csbaic.jd.dto.Activity;
import com.csbaic.jd.dto.CreateActivity;
import com.csbaic.jd.service.IActivityService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/activities")
@Api(value = "活动管理", tags = "活动管理")
@ResponseResult
public class AdminActivityController {

    @Autowired
    private IActivityService activityService;


    /**
     * 创建新的活动
     * @param activity
     * @return
     */
    @PostMapping
    @ApiOperation("创建活动")
    public Activity createActivity(@RequestBody CreateActivity activity){
        return activityService.createActivity(activity);
    }
}
