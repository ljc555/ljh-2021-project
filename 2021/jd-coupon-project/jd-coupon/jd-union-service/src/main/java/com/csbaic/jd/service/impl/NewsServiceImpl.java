package com.csbaic.jd.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.common.result.ResultCode;
import com.csbaic.jd.config.MiniAppConstants;
import com.csbaic.jd.dto.*;
import com.csbaic.jd.entity.MessageEntity;
import com.csbaic.jd.entity.NewsEntity;
import com.csbaic.jd.enums.ActivityStatus;
import com.csbaic.jd.enums.MessageStatus;
import com.csbaic.jd.enums.NewsStatus;
import com.csbaic.jd.mapper.NewsMapper;
import com.csbaic.jd.service.IActivityService;
import com.csbaic.jd.service.IMessageService;
import com.csbaic.jd.service.INewsService;
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

/**
 * <p>
 * 新闻 服务实现类
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-14
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, NewsEntity> implements INewsService {

    private final IActivityService activityService;

    private final IMessageService messageService;

    @Autowired
    public NewsServiceImpl(IActivityService activityService, IMessageService messageService) {
        this.activityService = activityService;
        this.messageService = messageService;
    }

    @Override
    public List<News> getVisibleNews() {
         List<NewsEntity> news = list(
                Wrappers.<NewsEntity>query()
                        .eq(NewsEntity.STATUS, NewsStatus.AUTO.getStatus())
                        .or()
                        .eq(NewsEntity.STATUS, NewsStatus.VISIBLE.getStatus())
        );


         return news.stream().filter(NewsServiceImpl::filterNews)
                 .map(NewsServiceImpl::map)
                 .collect(Collectors.toList());

    }


    private static boolean filterNews(NewsEntity news){
        if(Objects.equals(news.getStatus(), NewsStatus.VISIBLE.getStatus())){
            return true;
        }

        if(Objects.equals(news.getStatus(), NewsStatus.AUTO.getStatus())){
            return LocalDateTime.now().isBefore(news.getEndTime());
        }

        return false;
    }


    @Override
    @Transactional
    public List<News> createNewsByMessage(Ids messages) {
        if(messages.getIds() == null || messages.getIds().isEmpty()){
            throw new BizRuntimeException("活动Id不正确");
        }


        List<News> news = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        for(Long aid: messages.getIds()){
            MessageEntity act = messageService.getById(aid);

            if(act == null){
                throw BizRuntimeException.from(ResultCode.ERROR, "消息未找到: " + aid);
            }


            MessageStatus messageStatus = MessageStatus.statusOf(act.getStatus());
            if(messageStatus == null){
                throw BizRuntimeException.from(ResultCode.ERROR, "消息状态不正确: " + aid);
            }

            if(messageStatus == MessageStatus.INVISIBLE){
                throw BizRuntimeException.from(ResultCode.ERROR, "消息当前状态不可见: " + aid);
            }else if(messageStatus == MessageStatus.AUTO && now.isAfter(act.getEndTime())){
                throw BizRuntimeException.from(ResultCode.ERROR, "消息显示时期不在当前范围内: " + aid);
            }


            CreateNews createNews = new CreateNews();
            createNews.setStatus(messageStatus == MessageStatus.AUTO ? MessageStatus.AUTO.getStatus() : MessageStatus.VISIBLE.getStatus());
            createNews.setStartTime(
                    dateTimeFormatter.format(act.getStartTime())
            );
            createNews.setEndTime(
                    dateTimeFormatter.format(act.getEndTime())
            );
            createNews.setTitle(act.getTitle());
            createNews.setLocation(MiniAppConstants.MESSAGE_DETAIL + "?id=" + act.getId());
            news.add( createNews(createNews) );
        }

        return news;
    }

    @Override
    public News createNews(CreateNews news) {
        if(news == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST);
        }

        if(Strings.isNullOrEmpty(news.getLocation())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "news.location 不能为空");
        }

        if(Strings.isNullOrEmpty(news.getTitle())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "news.title 不能为空");
        }


        NewsStatus status = NewsStatus.statusOf(news.getStatus());
        if(status == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "news.status 值不正确");
        }



        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusYears(1);



        if(!Strings.isNullOrEmpty(news.getStartTime())){
            startTime = LocalDateTime.from(dateTimeFormatter.parse(news.getStartTime()));
        }

        if(!Strings.isNullOrEmpty(news.getEndTime())){
            endTime = LocalDateTime.from(dateTimeFormatter.parse(news.getEndTime()));
        }

        if (endTime.isBefore(startTime)) {
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "活动时间不正确");
        }


        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setLocation(news.getLocation());
        newsEntity.setTitle(news.getTitle());
        newsEntity.setStatus(news.getStatus());
        newsEntity.setStartTime(startTime);
        newsEntity.setEndTime(endTime);
        newsEntity.setStatus(status.getStatus());
        save(newsEntity);

        News ret = new News();
        BeanUtils.copyProperties(newsEntity, ret);
        return ret;
    }

    @Override
    @Transactional
    public List<News> createNewsByActivity(CreateNewsByActivity activities) {
        if(activities.getActivities() == null || activities.getActivities().isEmpty()){
            throw new BizRuntimeException("活动Id不正确");
        }


        List<News> news = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        for(Long aid: activities.getActivities()){
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


            CreateNews createNews = new CreateNews();
            createNews.setStatus(activityStatus == ActivityStatus.AUTO ? NewsStatus.AUTO.getStatus() : NewsStatus.VISIBLE.getStatus());
            createNews.setStartTime(
                    dateTimeFormatter.format(act.getStartTime())
            );
            createNews.setEndTime(
                    dateTimeFormatter.format(act.getEndTime())
            );
            createNews.setTitle(act.getTitle());
            createNews.setLocation(MiniAppConstants.ACTIVITY_DETAIL + "?id=" + act.getId());
            news.add( createNews(createNews) );
        }

        return news;

    }

    private static  News map(NewsEntity entity){
        News news = new News() ;
        org.springframework.beans.BeanUtils.copyProperties(entity, news);
        return news;
    }
}
