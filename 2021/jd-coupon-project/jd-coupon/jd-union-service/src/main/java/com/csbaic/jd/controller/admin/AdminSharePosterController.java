package com.csbaic.jd.controller.admin;


import com.csbaic.jd.dto.CreateSharePoster;
import com.csbaic.jd.service.ISharePosterService;
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

/**
 * <p>
 * 分享海报 前端控制器
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-23
 */
@Api(value = "管理海报", tags = "海报管理")
@RequestMapping("/admin/posters")
@RestController
@ResponseResult
public class AdminSharePosterController {


    @Autowired
    private ISharePosterService sharePosterService;

    /**
     * 获取海报
     * @param userId
     * @param poster
     */
    @ApiOperation("创建分享海报")
    @PostMapping
    public void createPoster(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId,@RequestBody CreateSharePoster poster){
        sharePosterService.createPoster(userId, poster);
    }

}

