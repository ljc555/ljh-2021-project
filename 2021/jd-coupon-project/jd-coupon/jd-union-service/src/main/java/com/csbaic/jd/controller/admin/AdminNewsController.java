package com.csbaic.jd.controller.admin;

import com.csbaic.jd.dto.CreateNews;
import com.csbaic.jd.dto.CreateNewsByActivity;
import com.csbaic.jd.dto.Ids;
import com.csbaic.jd.dto.News;
import com.csbaic.jd.service.INewsService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/news")
@Api(value = "快报", tags = "快报")
@ResponseResult
public class AdminNewsController {

    @Autowired
    private INewsService newsService;


    /**
     * 添加一条
     */
    @ApiOperation("添加快报")
    @PostMapping()
    public News createNews(@RequestBody CreateNews news){
        return newsService.createNews(news);
    }

    /**
     * 添加一条
     */
    @ApiOperation("使用活动创建快报")
    @PostMapping("/news_by_activities")
    public List<News> createNews(@RequestBody CreateNewsByActivity createNewsByActivity){
        return newsService.createNewsByActivity(createNewsByActivity);
    }

    /**
     * 添加一条
     */
    @ApiOperation("使用消息创建快报")
    @PostMapping("/news_by_messages")
    public List<News> createNews(@RequestBody Ids createNewsByMessage){
        return newsService.createNewsByMessage(createNewsByMessage);
    }


}
