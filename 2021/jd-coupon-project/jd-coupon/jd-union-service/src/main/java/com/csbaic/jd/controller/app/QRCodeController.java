package com.csbaic.jd.controller.app;

import com.csbaic.jd.dto.Base64QRCode;
import com.csbaic.jd.dto.CreateGoodsQRCode;
import com.csbaic.jd.dto.CreateMiniAppQRCode;
import com.csbaic.jd.service.qrcode.QRCodeService;
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
@RequestMapping("/qrcode")
@Api(value = "二维码", tags = "二维码")
@ResponseResult
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;

    /**
     * 创建商品二维码
     * @return 快报
     */
    @ApiOperation("创建商品海报二维码")
    @PostMapping("/goods")
    public Base64QRCode createQRCodeForGoods(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId, @RequestBody CreateGoodsQRCode createGoodsQRCode){
        return  qrCodeService.createQRCodeForGoods(userId, createGoodsQRCode);
    }

    /**
     * 创建小程序推广二维码
     * @return
     */
    @ApiOperation("创建小程序海报二维码")
    @PostMapping("/mini_app")
    public Base64QRCode createQRCodeForGoods(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId,@RequestBody CreateMiniAppQRCode createGoodsQRCode){
        return  qrCodeService.createQRCodeForMiniApp(userId, createGoodsQRCode);
    }

    /**
     * 创建小程序推广二维码
     * @return
     */
    @ApiOperation("创建推广海报二维码")
    @PostMapping("/rec_mini_app")
    public Base64QRCode createQRCodeForMiniApp(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId,@RequestBody CreateMiniAppQRCode createGoodsQRCode){
        return  qrCodeService.createQRCodeForMiniApp(userId, createGoodsQRCode);
    }




}
