package com.csbaic.jd.controller.admin;

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
@RequestMapping("/admin/banners")
@Api(value = "滚动图", tags = "滚动图")
@ResponseResult
public class AdminBannerController {

    @Autowired
    private IBannerService bannerService;


    /**
     * 创建一个新的banner
     * @param banner
     * @return
     */
    @PostMapping
    @ApiOperation("创建banner")
    public Banner createBanner(@RequestBody CreateBanner banner){
        return bannerService.createBanner(banner);
    }


    /**
     * 创建一个新的banner
     * @param banner
     * @return
     */
    @PostMapping("/by_activity")
    @ApiOperation("通过活动Id创建Banner")
    public List<Banner> createBannerByActivity(@RequestBody CreateBannerByActivity banner){
        return bannerService.createBannerByActivity(banner);
    }



}
