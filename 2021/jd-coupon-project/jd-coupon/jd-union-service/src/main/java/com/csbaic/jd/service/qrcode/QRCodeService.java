package com.csbaic.jd.service.qrcode;

import com.csbaic.jd.dto.CreateGoodsQRCode;
import com.csbaic.jd.dto.Base64QRCode;
import com.csbaic.jd.dto.CreateMiniAppQRCode;

/**
 * 二维码服务
 */
public interface QRCodeService {


    /**
     * 创建商品二维码
     * @return
     */
    Base64QRCode createQRCodeForGoods(Long userId, CreateGoodsQRCode createGoodsQRCode);

    /**
     * 创建小程序二维码
     * @param userId
     * @param createMiniAppQRCode
     * @return
     */
    Base64QRCode createQRCodeForMiniApp(Long userId,  CreateMiniAppQRCode createMiniAppQRCode);
}
