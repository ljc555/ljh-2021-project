package com.csbaic.jd.controller.admin;


import com.csbaic.jd.dto.CreateMessage;
import com.csbaic.jd.dto.Message;
import com.csbaic.jd.service.IMessageService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 新闻 前端控制器
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-26
 */
@RestController
@RequestMapping("/admin/messages")
@Api(value = "消息管理", tags = "消息管理")
@ResponseResult
public class AdminMessageController {


    @Autowired
    private IMessageService messageService;


    @ApiOperation("创建消息")
    @PostMapping("/")
    Message createMessage(@RequestBody  CreateMessage message){
        return messageService.createMessage(message);
    }


}

