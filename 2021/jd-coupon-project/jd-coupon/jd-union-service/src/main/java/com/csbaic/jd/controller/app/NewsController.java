package com.csbaic.jd.controller.app;

import com.csbaic.jd.dto.News;
import com.csbaic.jd.service.INewsService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
@Api(value = "快报", tags = "快报")
@ResponseResult
public class NewsController {

    @Autowired
    private INewsService newsService;

    /**
     * 获取可见新闻
     * @return 快报
     */
    @ApiOperation("获取可见的新闻列表")
    @GetMapping()
    public List<News> getNews(){
        return newsService.getVisibleNews();
    }




}
