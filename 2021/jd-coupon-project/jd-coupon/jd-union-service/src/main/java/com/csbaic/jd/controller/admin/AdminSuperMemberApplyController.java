package com.csbaic.jd.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.dto.RejectSuperMemberApply;
import com.csbaic.jd.dto.SuperMemberApplyOrderInfo;
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
@RequestMapping("/admin/super_members")
@Api(value = "超级会员申请处理", tags = "超级会员申请处理")
@ResponseResult
public class AdminSuperMemberApplyController {

    @Autowired
    ISuperMemberApplyService superMemberApplyService;

    @ApiOperation("获取超级会员申请单")
    @GetMapping("/")
    public IPage<SuperMemberApplyOrderInfo> getSuperMemberApplyOrderInfo(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId,
                                                                         @ApiParam(value = "申请状态（2：已提交，3：通过，4：拒绝）", allowableValues = "2, 3, 4") @RequestParam(value = "status", required = false) Integer status,
                                                                         @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
                                                                         @RequestParam(value = "pageSize", defaultValue = "20") int pageSize
    ){
        return superMemberApplyService.getSuperMemberApplyOrderInfo(userId, status, pageIndex, pageSize );
    }


    @ApiOperation("同意群组申请")
    @PostMapping("/approve/{id}")
    public void approveSuperMemberApply(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId, @PathVariable("id") Long applyId){
         superMemberApplyService.approveSuperMemberApply(userId, applyId);
    }

    @ApiOperation("拒绝群组申请")
    @PostMapping("/reject/{id}")
    public void rejectSuperMemberApply(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId,
                                       @PathVariable("id") Long applyId,
                                       @RequestBody RejectSuperMemberApply rejectSuperMemberApply
                                       ){
        superMemberApplyService.rejectSuperMemberApply(userId, applyId ,rejectSuperMemberApply);
    }


}

