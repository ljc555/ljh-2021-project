package com.csbaic.jd.controller.app;

import com.csbaic.jd.dto.CreateFeedback;
import com.csbaic.jd.service.IFeedbackService;
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

@RestController
@RequestMapping("/feedback")
@Api(value = "反馈", tags = "反馈")
@ResponseResult
public class FeedbackController {

    @Autowired
    private IFeedbackService feedbackService;


    /**
     * 创建反馈
     */
    @ApiOperation("添加反馈信息")
    @PostMapping("/")
    public void createFeedback(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId, @RequestBody CreateFeedback feedback){
         feedbackService.createFeedback(userId, feedback);
    }


}
