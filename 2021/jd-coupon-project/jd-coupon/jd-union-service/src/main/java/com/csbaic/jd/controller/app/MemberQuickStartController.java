package com.csbaic.jd.controller.app;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.dto.MemberQuickStart;
import com.csbaic.jd.service.IMemberQuickStartService;
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
 * 新人上手 前端控制器
 * </p>
 *
 * @author yjwfn
 * @since 2020-04-14
 */
@Api( tags = "新手上路")
@RequestMapping("/quick_start")
@RestController
@ResponseResult
public class MemberQuickStartController {



    private final IMemberQuickStartService quickStartService;

    @Autowired
    public MemberQuickStartController(IMemberQuickStartService quickStartService) {
        this.quickStartService = quickStartService;
    }
    @ApiOperation(("获取新手上路"))
    @GetMapping
    public IPage<MemberQuickStart> getQuickStart(
            @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {

        return quickStartService.getQuickStart(pageIndex, pageSize);
    }
}

