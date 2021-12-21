package com.csbaic.jd.controller.app;

import com.csbaic.jd.dto.Banner;
import com.csbaic.jd.dto.CreateBanner;
import com.csbaic.jd.dto.CreateBannerByActivity;
import com.csbaic.jd.service.IBannerService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banners")
@Api(value = "滚动图", tags = "滚动图")
@ResponseResult
public class BannerController {

    @Autowired
    private IBannerService bannerService;

    /**
     * 获取可用的Banners
     * @return banner列表
     */
    @ApiOperation("获取可用的banner")
    @GetMapping
    public List<Banner> getAvailableBanners(){
        return bannerService.getAvailableBanners();
    }


}
