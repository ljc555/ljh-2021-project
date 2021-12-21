package com.csbaic.jd.controller.app;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.dto.SharePoster;
import com.csbaic.jd.service.ISharePosterService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 分享海报 前端控制器
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-23
 */
@Api(value = "分享海报", tags = "分享海报")
@RequestMapping("/posters")
@RestController
@ResponseResult
public class SharePosterController {


    @Autowired
    private ISharePosterService sharePosterService;

    /**
     * 获取海报

     */
    @ApiOperation("获取分享海报")
    @GetMapping
    public IPage<SharePoster> getSharePosters(@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize ){
        return sharePosterService.getPosters(pageIndex, pageSize);
    }


}

