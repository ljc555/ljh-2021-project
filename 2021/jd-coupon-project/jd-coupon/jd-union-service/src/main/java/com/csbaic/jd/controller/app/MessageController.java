package com.csbaic.jd.controller.app;


import com.csbaic.jd.dto.Message;
import com.csbaic.jd.service.IMessageService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 新闻 前端控制器
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-26
 */
@RestController
@RequestMapping("/messages")
@Api(value = "消息", tags = "消息")
@ResponseResult
public class MessageController {


    @Autowired
    private IMessageService messageService;

    @ApiOperation("获取消息详情")
    @GetMapping("/{id}")
    Message getMessageById(@PathVariable("id") Long id){
        return messageService.getValidMessage(id);
    }
}

