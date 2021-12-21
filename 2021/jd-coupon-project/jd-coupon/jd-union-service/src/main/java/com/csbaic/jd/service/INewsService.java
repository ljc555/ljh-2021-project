package com.csbaic.jd.service;

import com.csbaic.jd.dto.CreateNews;
import com.csbaic.jd.dto.CreateNewsByActivity;
import com.csbaic.jd.dto.Ids;
import com.csbaic.jd.dto.News;
import com.csbaic.jd.entity.NewsEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 新闻 服务类
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-14
 */
public interface INewsService extends IService<NewsEntity> {


    /**
     * 获取可见的新闻
     * @return
     */
    List<News> getVisibleNews();

    /**
     * 创建一个新的新闻
     * @param news
     * @return
     */
    News createNews(CreateNews news);

    /**
     * 创建一个新的新闻
     * @param messages
     * @return
     */
    List<News> createNewsByMessage(Ids messages);


    /**
     * 创建一个新的新闻
     * @param activities
     * @return
     */
    List<News> createNewsByActivity(CreateNewsByActivity activities);

}
