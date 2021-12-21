package com.csbaic.jd.service;

import com.csbaic.jd.dto.Activity;
import com.csbaic.jd.dto.ActivityDetail;
import com.csbaic.jd.dto.CreateActivity;
import com.csbaic.jd.entity.ActivityEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 活动表 服务类
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-14
 */
public interface IActivityService extends IService<ActivityEntity> {


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
