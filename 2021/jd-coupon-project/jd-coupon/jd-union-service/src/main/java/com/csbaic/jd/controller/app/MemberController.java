package com.csbaic.jd.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.dto.GradeMemberInfo;
import com.csbaic.jd.dto.MemberFeeSegments;
import com.csbaic.jd.dto.QueryMember;
import com.csbaic.jd.dto.SimpleMemberUserInfo;
import com.csbaic.jd.service.user.MemberService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(value = "会员中心", tags = "会员中心")
@RequestMapping("/members")
@RestController
@ResponseResult
public class MemberController {



    @Autowired
    private MemberService memberService;


    /**
     * 获取会员信息
     * @return
     */
    @ApiOperation("获取会员信息")
    @GetMapping("/info")
    public GradeMemberInfo getMemberInfo(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId){
        return memberService.getGradeMemberInfo(userId);
    }

    /**
     * 查询用户的成员列表信息
     * @return
     */
    @ApiOperation("查询用户的成员列表信息")
    @PostMapping("/")
    public IPage<SimpleMemberUserInfo> getMembersOf(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId, @RequestBody QueryMember queryMember){
        return memberService.getMembersOf(userId, queryMember);
    }



    /**
     * 获取我的收益
     * @return
     */
    @ApiOperation("用户收益详情")
    @PostMapping("/fee_data")
    public MemberFeeSegments getFeeData(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId){
        return memberService.getMemberFeeBlocks(userId);
    }

}
