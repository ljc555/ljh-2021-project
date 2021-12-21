package com.csbaic.jd.controller.app;

import com.csbaic.jd.dto.*;
import com.csbaic.jd.service.GoodsUrlService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Api(value = "商品链接接口", tags = "商品链接接口")
@RequestMapping("/goods_url")
@RestController
@ResponseResult
public class GoodsUrlController {

    @Autowired
    private GoodsUrlService  goodsUrlService;

    @ApiOperation("获取商品推广连接（短链接）")
    @PostMapping("/short_url")
    public GoodsRecUrl createShortUrl(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId, @RequestBody CreateGoodsShortUrl shortUrl){
        return goodsUrlService.createGoodsShortUrl(userId, shortUrl);
    }

    @ApiOperation("生成商品购买链接（短链接）")
    @PostMapping("/purchase_url")
    public GoodsRecUrl createPurchaseShortUrl(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId, @RequestBody CreatePurchaseShortUrl shortUrl){
        return goodsUrlService.createPurchaseShortUrl(userId, shortUrl);
    }


    @ApiOperation("商品转链")
    @PostMapping("/convert")
    public ConvertUrlResult getConvertedUrl(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId, @RequestBody ConvertUrlContent convertUrlContent){
        return goodsUrlService.createConvertedUrl(userId, convertUrlContent);
    }

}
