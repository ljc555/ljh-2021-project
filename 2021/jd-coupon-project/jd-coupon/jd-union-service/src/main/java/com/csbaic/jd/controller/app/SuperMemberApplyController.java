package com.csbaic.jd.controller.app;


import com.csbaic.jd.dto.ApplySuperMember;
import com.csbaic.jd.dto.SuperMemberApplyInfo;
import com.csbaic.jd.service.ISuperMemberApplyService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 超级会员申请表 前端控制器
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-24
 */
@RestController
@RequestMapping("/super_members")
@Api(value = "超级会员申请", tags = "超级会员申请")
@ResponseResult
public class SuperMemberApplyController {

    @Autowired
    ISuperMemberApplyService superMemberApplyService;

    @ApiOperation("获取群组信息（预申请）")
    @GetMapping("")
    public SuperMemberApplyInfo getSuperMemberApplyInfo(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId){
        return superMemberApplyService.getSuperMemberApplyInfo(userId);
    }


    @ApiOperation("提交/更新群组申请")
    @PostMapping("")
    public void submitSuperMemberApply(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId,@RequestBody ApplySuperMember applySuperMember){
         superMemberApplyService.submitApplySuperMember(userId, applySuperMember);
    }

}

