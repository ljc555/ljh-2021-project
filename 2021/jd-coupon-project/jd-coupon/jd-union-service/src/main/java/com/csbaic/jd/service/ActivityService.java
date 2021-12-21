package com.csbaic.jd.service;

import com.csbaic.jd.dto.Activity;
import com.csbaic.jd.dto.CreateActivity;

/***
 * 活动服务
 */
public interface ActivityService {


    /**
     * 获取活动详情
     * @param activityId
     * @return
     */
    Activity getActivityById(Long activityId);


    /***
     * 创建活动
     * @param activity
     * @return
     */
    Activity createActivity(CreateActivity activity);

}
