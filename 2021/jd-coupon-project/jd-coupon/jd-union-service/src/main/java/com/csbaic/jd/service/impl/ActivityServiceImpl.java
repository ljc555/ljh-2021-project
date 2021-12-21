package com.csbaic.jd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.common.result.ResultCode;
import com.csbaic.jd.dto.Activity;
import com.csbaic.jd.dto.CreateActivity;
import com.csbaic.jd.entity.ActivityEntity;
import com.csbaic.jd.enums.ActivityStatus;
import com.csbaic.jd.mapper.ActivityMapper;
import com.csbaic.jd.service.IActivityService;
import com.google.common.base.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ActivityServiceImpl  extends ServiceImpl<ActivityMapper, ActivityEntity> implements IActivityService {




    @Override
    public Activity getActivityById(Long activityId) {
        ActivityEntity activityEntity =  getById(activityId);

        if(activityEntity == null){
            throw BizRuntimeException.from(ResultCode.NOT_FOUND, "未找到活动");
        }

        if(activityEntity.getEndTime() != null && activityEntity.getEndTime().isBefore(LocalDateTime.now())){
            throw BizRuntimeException.from(ResultCode.NOT_FOUND, "活动已结束");
        }




        Activity activity = new Activity();
        BeanUtils.copyProperties(activityEntity, activity);
        return activity;
    }

    @Override
    public Activity createActivity(CreateActivity activity) {

        if(activity == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "参数错误");
        }

        if(Strings.isNullOrEmpty(activity.getTitle())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "缺少活动标题");
        }

        if(Strings.isNullOrEmpty(activity.getContent())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "缺少活动内容");
        }


        if(Strings.isNullOrEmpty(activity.getShareImage())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "缺少活动分享图片");
        }


        if(Strings.isNullOrEmpty(activity.getLogoImage())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "缺少活动Logo图片");
        }

//
//        if(Strings.isNullOrEmpty(activity.getStartTime())){
//            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "活动开始时间不能为空");
//        }
//
//        if(Strings.isNullOrEmpty(activity.getEndTime())){
//            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "活动结束时间不能为空");
//        }

        ActivityStatus activityStatus = ActivityStatus.statusOf(activity.getStatus());
        if(activityStatus == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "状态状态不正确");
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusYears(1);


        if(!Strings.isNullOrEmpty(activity.getStartTime())){
            startTime = LocalDateTime.from(dateTimeFormatter.parse(activity.getStartTime()));
        }

        if(!Strings.isNullOrEmpty(activity.getEndTime())){
            endTime = LocalDateTime.from(dateTimeFormatter.parse(activity.getEndTime()));
        }



        if (endTime.isBefore(startTime)) {
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "活动时间不正确");
        }



        ActivityEntity activityEntity = new ActivityEntity();
        BeanUtils.copyProperties(activity, activityEntity);
        activityEntity.setStartTime(startTime);
        activityEntity.setEndTime(endTime);
        activityEntity.setStatus(activityStatus.getStatus());
        save(activityEntity);

        Activity ret = new Activity();
        BeanUtils.copyProperties(activityEntity, ret);
        return ret;
    }
}
