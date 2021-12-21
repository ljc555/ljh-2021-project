package com.csbaic.jd.controller.admin;


import com.csbaic.jd.dto.MemberQuickStart;
import com.csbaic.jd.service.IMemberQuickStartService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 新人上手 前端控制器
 * </p>
 *
 * @author yjwfn
 * @since 2020-04-14
 */
@Api(value = "新手上路管理", tags = "新手上路管理")
@RequestMapping("/admin/quick_start")
@RestController
@ResponseResult
public class AdminMemberQuickStartController {



    private final IMemberQuickStartService quickStartService;

    @Autowired
    public AdminMemberQuickStartController(IMemberQuickStartService quickStartService) {
        this.quickStartService = quickStartService;
    }

    @ApiOperation(("创建新手上路"))
    @PostMapping
    public void getQuickStart(@RequestBody MemberQuickStart quickStart){
        quickStartService.save(quickStart);
    }

}

