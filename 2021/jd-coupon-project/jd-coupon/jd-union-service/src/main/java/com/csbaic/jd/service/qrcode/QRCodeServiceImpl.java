package com.csbaic.jd.service.qrcode;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaCodeLineColor;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.csbaic.jd.config.application.ApplicationProperties;
import com.csbaic.jd.dto.*;
import com.csbaic.jd.entity.UserEntity;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.jd.enums.UserIdentify;
import com.csbaic.jd.service.GoodsService;
import com.csbaic.jd.service.IUserService;
import com.csbaic.jd.service.order.SubUnionId;
import com.csbaic.jd.service.order.SubUnionIdConverter;
import com.csbaic.common.result.ResultCode;
import com.google.common.base.Strings;
import com.google.zxing.EncodeHintType;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class QRCodeServiceImpl implements QRCodeService {

    private final IUserService userService;

    private final GoodsService goodsService;

    private final ResourceLoader resourceLoader;

    private final ApplicationProperties properties;

    private final WxMaService maService;

    private final SubUnionIdConverter subUnionIdConverter;

    @Autowired
    public QRCodeServiceImpl(IUserService userService, GoodsService goodsService, ResourceLoader resourceLoader, ApplicationProperties properties, WxMaService maService, SubUnionIdConverter subUnionIdConverter) {
        this.userService = userService;
        this.goodsService = goodsService;
        this.resourceLoader = resourceLoader;
        this.properties = properties;

        this.maService = maService;
        this.subUnionIdConverter = subUnionIdConverter;
    }


    @Override
    public Base64QRCode createQRCodeForGoods(Long userId, CreateGoodsQRCode createGoodsQRCode) {

        if(userId == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "用户id不正确");
        }


        if(createGoodsQRCode == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "错误的请求参数");
        }


        if(Strings.isNullOrEmpty(createGoodsQRCode.getMaterialUrl())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "商品地址不能为空");
        }

        int count = userService.count(
                Wrappers.<UserEntity>query()
                .eq(UserEntity.ID, userId)
        );

        if(count <= 0){
            throw BizRuntimeException.from(ResultCode.NOT_FOUND, "用户不存在");
        }

        UserIdentify identify = userService.getUserIdentifyById(userId);
        SubUnionId subUnionId = new SubUnionId();
        subUnionId.setOwner(userId);
        subUnionId.setIdentifyOfOwner(identify.getCode());

        int size = createGoodsQRCode.getSize() == null ? 72 : createGoodsQRCode.getSize();
        GetRecUrl recUrl = new GetRecUrl();
        recUrl.setCouponUrl(createGoodsQRCode.getCouponUrl());
        recUrl.setMaterialId(createGoodsQRCode.getMaterialUrl());
        recUrl.setSubUnionId(subUnionIdConverter.covert(subUnionId));

        log.debug("Get goods rebate url {}", recUrl);
        GoodsRecUrl goodsRecUrl = goodsService.getRecUrlBySubUnionId(recUrl);

        if(goodsRecUrl == null || Strings.isNullOrEmpty(goodsRecUrl.getShortURL())){
            throw BizRuntimeException.from(ResultCode.ERROR, "获取商品推广链接失败");
        }

        log.debug("Rebate url {}", goodsRecUrl);

        log.debug("Create qrcode {}", goodsRecUrl.getShortURL());

        ByteArrayOutputStream bos  = new ByteArrayOutputStream();
        try {
            InputStream logoStream = resourceLoader.getResource(properties.getGoodsQrcodeLogo())
                    .getInputStream();

            Map<EncodeHintType, Object> hint = new HashMap<>();
            hint.put(EncodeHintType.MARGIN, 0);
            BufferedImage qrcodeImage =  QRCodeKit.createQRCode(goodsRecUrl.getShortURL(), "utf-8", hint, size, size);
            ImageIO.write(qrcodeImage, "png", bos);
            logoStream.close();
        } catch (IOException e) {
            log.error("", e);
            throw BizRuntimeException.from(ResultCode.ERROR, "生成商品二维码失败");
        }finally {
            try {
                bos.close();
            } catch (IOException ignore) {

            }
        }

        String qrcodeAsString =  Base64.encodeBase64String(bos.toByteArray());
        Base64QRCode base64QrCode = new Base64QRCode();
        base64QrCode.setContent(qrcodeAsString);

        return base64QrCode;
    }

    @Override
    public Base64QRCode createQRCodeForMiniApp(Long userId, CreateMiniAppQRCode createMiniAppQRCode) {
        String token = null;
        try {
            token = maService.getAccessToken();
        } catch (WxErrorException e) {
            log.error("", e);
            throw BizRuntimeException.from(ResultCode.ERROR, "获取小程序AccessToken失败");
        }

        if(Strings.isNullOrEmpty(token)){
            throw BizRuntimeException.from(ResultCode.ERROR, "获取小程序AccessToken失败");
        }

//        if(createMiniAppQRCode.getScene() == null){
//            throw BizRuntimeException.from(ResultCode.ERROR, "获取小程序Scene参数失败");
//        }

        UserEntity userEntity = userService.getById(userId);
        if(userEntity == null){
            throw  BizRuntimeException.from(ResultCode.NOT_FOUND, "用户Id不正确");
        }

        RGBColor rgbColor = createMiniAppQRCode.getLineColor();
        Map<String, Object> sceneMap = createMiniAppQRCode.getScene() == null ? new HashMap<>() : createMiniAppQRCode.getScene();
        sceneMap.put("ic" , userEntity.getInvitationCode());
        StringBuilder scene = new StringBuilder();
        for(Map.Entry<String, Object> entry : sceneMap.entrySet()){
            scene.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }

        if(scene.length() > 0){
            scene.deleteCharAt(scene.length() - 1);
        }

        try {
            File qrcodeFile = maService.getQrcodeService().createWxaCodeUnlimit(scene.toString(),
                    createMiniAppQRCode.getPage(),
                    createMiniAppQRCode.getSize(),
                    createMiniAppQRCode.getAutoColor() != null && createMiniAppQRCode.getAutoColor(),
                    rgbColor != null ? new WxMaCodeLineColor(rgbColor.getR(), rgbColor.getG(), rgbColor.getB()) : null,
                    createMiniAppQRCode.getIsHyaline() != null && createMiniAppQRCode.getIsHyaline());

            log.info("generate mini app qrcode {}", qrcodeFile.getAbsoluteFile());
            byte[] bytes = IOUtils.toByteArray(new FileInputStream(qrcodeFile));
            String qrcodeAsString = Base64.encodeBase64String(bytes);
            Base64QRCode base64QrCode = new Base64QRCode();
            base64QrCode.setContent(qrcodeAsString);
            return base64QrCode;
        } catch (WxErrorException e) {
            log.error("", e);
            throw BizRuntimeException.from(ResultCode.ERROR, "生成小程序二维码失败");
        }  catch (IOException e) {
            log.error("", e);
            throw BizRuntimeException.from(ResultCode.ERROR, "生成小程序二维码失败");
        }

    }
}
