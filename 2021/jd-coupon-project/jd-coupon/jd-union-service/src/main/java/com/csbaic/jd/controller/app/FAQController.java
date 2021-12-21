package com.csbaic.jd.controller.app;

import com.csbaic.jd.dto.CreateQuesCategory;
import com.csbaic.jd.dto.CreateQuestion;
import com.csbaic.jd.dto.GroupQuestion;
import com.csbaic.jd.service.IQuestionCategoryService;
import com.csbaic.jd.service.IQuestionService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faq")
@Api(value = "常见问题", tags = "常见问题")
@ResponseResult
public class FAQController {

    @Autowired
    private IQuestionCategoryService questionCategoryService;

    @Autowired
    private IQuestionService questionService;

    /**
     * 创建问题分类

     */
    @PostMapping("/categories")
    @ApiOperation("创建问题分类")
    public void addCategory(@RequestBody CreateQuesCategory body){
        questionCategoryService.addCategory( body);
    }


    /**
     * 添加问题

     */
    @PostMapping("/questions")
    @ApiOperation("创建问题")
    public void addQuestion(@RequestBody CreateQuestion question){
        questionService.create(question);
    }




    /**
     * 获取分组后问题
     */
    @GetMapping("")
    @ApiOperation("获取分组后问题")
    public List<GroupQuestion> getGroupQuestion(@RequestParam(value = "cid", required = false) Long cid){
        return questionCategoryService.getGroupQuestion(cid);
    }




}
