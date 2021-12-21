package com.csbaic.jd.controller.app;

import com.csbaic.jd.dto.GoodsCategory;
import com.csbaic.jd.service.GoodsService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value = "分类", tags = "商品分类")
@RequestMapping("/categories")
@ResponseResult
public class CategoryController {




    @Autowired
    private GoodsService goodsService;


    @ApiOperation("获取商品分类")
    @GetMapping("")
    public  List<GoodsCategory> getCategories(@ApiParam("父类目id(一级父类目为0)") @RequestParam(value = "parentId", defaultValue = "0") Integer parentId, @ApiParam("类目级别(类目级别 0，1，2 代表一、二、三级类目)") @RequestParam(value = "grade", defaultValue = "0") Integer grade){
        return goodsService.getCategories(parentId, grade);
    }



}
