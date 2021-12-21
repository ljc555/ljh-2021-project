package com.csbaic.jd.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.common.result.ResultCode;
import com.csbaic.jd.dto.Activity;
import com.csbaic.jd.dto.Banner;
import com.csbaic.jd.dto.CreateBanner;
import com.csbaic.jd.dto.CreateBannerByActivity;
import com.csbaic.jd.entity.BannerEntity;
import com.csbaic.jd.enums.ActivityStatus;
import com.csbaic.jd.enums.BannerStatus;
import com.csbaic.jd.mapper.BannerMapper;
import com.csbaic.jd.service.IActivityService;
import com.csbaic.jd.service.IBannerService;
import com.google.common.base.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BannerServiceImpl  extends ServiceImpl<BannerMapper, BannerEntity> implements IBannerService {


    @Autowired
    private IActivityService activityService;



    @Override
    public List<Banner> getAvailableBanners() {

        List<BannerEntity> entities = list(
                Wrappers.<BannerEntity>query()
                .eq(BannerEntity.STATUS, BannerStatus.AUTO.getStatus())
                .or()
                .eq(BannerEntity.STATUS, BannerStatus.VISIBLE.getStatus())
        );


        return entities.stream()
                .filter(Objects::nonNull)
                .filter(BannerServiceImpl::filterBanners)
                .map(bannerEntity -> {
                    Banner banner = new Banner();
                    BeanUtils.copyProperties(bannerEntity, banner);
                    return banner;
                })
                .collect(Collectors.toList());

    }

    private static boolean filterBanners(BannerEntity bannerEntity){
        if(Objects.equals(bannerEntity.getStatus(), BannerStatus.VISIBLE.getStatus())){
            return true;
        }

        if(Objects.equals(bannerEntity.getStatus(), BannerStatus.AUTO.getStatus())){
            return LocalDateTime.now().isBefore(bannerEntity.getEndTime());
        }

        return false;
    }



    @Transactional
    @Override
    public Banner createBanner(CreateBanner banner) {

        if(Strings.isNullOrEmpty(banner.getCoverUrl())){
            throw new BizRuntimeException("Banner封面错误");
        }


        if(Strings.isNullOrEmpty(banner.getLocation())){
            throw new BizRuntimeException("跳转路径不能为空");
        }



        BannerStatus status = BannerStatus.statusOf(banner.getStatus());

        if(status == null){
            throw new BizRuntimeException("活动状态不正确");
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusYears(1);



        if(!Strings.isNullOrEmpty(banner.getStartTime())){
            startTime = LocalDateTime.from(dateTimeFormatter.parse(banner.getStartTime()));
        }

        if(!Strings.isNullOrEmpty(banner.getEndTime())){
            endTime = LocalDateTime.from(dateTimeFormatter.parse(banner.getEndTime()));
        }

        if (endTime.isBefore(startTime)) {
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "Banner时间不正确");
        }




        BannerEntity entity = new BannerEntity();
        BeanUtils.copyProperties(banner, entity);
        entity.setStartTime(startTime);
        entity.setEndTime(endTime);
        entity.setStatus(status.getStatus());
        save(entity);

        Banner bannerVo = new Banner();
        BeanUtils.copyProperties(entity, bannerVo);
        return bannerVo;
    }

    @Override
    public List<Banner> createBannerByActivity(CreateBannerByActivity activity) {
        if(activity.getActivities() == null || activity.getActivities().isEmpty()){
            throw new BizRuntimeException("活动Id不正确");
        }


        List<Banner> banners = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        for(Long aid: activity.getActivities()){
            Activity act = activityService.getActivityById(aid);

            if(act == null){
                throw BizRuntimeException.from(ResultCode.ERROR, "活动未找到: " + aid);
            }



            ActivityStatus activityStatus = ActivityStatus.statusOf(act.getStatus());
            if(activityStatus == null){
                throw BizRuntimeException.from(ResultCode.ERROR, "活动状态不正确: " + aid);
            }

            if(activityStatus == ActivityStatus.INVISIBLE){
                throw BizRuntimeException.from(ResultCode.ERROR, "活动当前状态不可见: " + aid);
            }else if(activityStatus == ActivityStatus.AUTO && now.isAfter(act.getEndTime())){
                throw BizRuntimeException.from(ResultCode.ERROR, "活动已经结束: " + aid);
            }

            CreateBanner createBanner = new CreateBanner();
            createBanner.setCoverUrl(act.getLogoImage());
            createBanner.setStartTime(
                    dateTimeFormatter.format(act.getStartTime())
            );
            createBanner.setEndTime(
                    dateTimeFormatter.format(act.getEndTime())
            );

            createBanner.setStatus(activityStatus == ActivityStatus.AUTO ? BannerStatus.AUTO.getStatus() : BannerStatus.VISIBLE.getStatus());
            createBanner.setTitle(act.getTitle());
            createBanner.setLocation("/page_package/activity/activity?id=" + act.getId());
            banners.add( createBanner(createBanner) );
        }


        return banners;
    }
}
