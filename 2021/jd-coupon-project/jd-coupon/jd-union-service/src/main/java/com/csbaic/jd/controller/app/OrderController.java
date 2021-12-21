package com.csbaic.jd.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.dto.OrderInfo;
import com.csbaic.jd.dto.QueryOrder;
import com.csbaic.jd.service.IOrderDetailService;
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
@RequestMapping("/order")
@Api(value = "订单管理", tags = "订单管理")
@ResponseResult
public class OrderController {

    @Autowired
    private IOrderDetailService orderDetailService;

    /**
     * 按时间维度查询订单
     * @return 订单信息
     */
    @ApiOperation("按时间维度查询订单")
    @PostMapping("/by_time")
    public IPage<OrderInfo> queryOrderByTime(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId, @RequestBody QueryOrder queryOrder){
        return  orderDetailService.queryOrderByTime(userId, queryOrder);
    }

}
