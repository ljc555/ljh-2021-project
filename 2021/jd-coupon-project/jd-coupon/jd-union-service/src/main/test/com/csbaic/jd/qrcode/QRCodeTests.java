package com.csbaic.jd.qrcode;

import com.csbaic.jd.JdSocialApplication;
import com.csbaic.jd.commission.CommissionConfiguration;
import com.csbaic.jd.commission.MockMemberContainerBuilder;
import com.csbaic.jd.dto.Base64QRCode;
import com.csbaic.jd.dto.CreateGoodsQRCode;
import com.csbaic.jd.dto.GoodsOrder;
import com.csbaic.jd.dto.SkuInfo;
import com.csbaic.jd.enums.UserIdentify;
import com.csbaic.jd.service.commission.CommissionStrategy;
import com.csbaic.jd.service.commission.MemberCommissionInfo;
import com.csbaic.jd.service.order.handle.filter.MemberCommissionSaver;
import com.csbaic.jd.service.qrcode.QRCodeService;
import com.csbaic.jd.service.team.MemberContainer;
import com.csbaic.jd.service.team.bean.MemberNode;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yjwfn on 2020/2/16.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JdSocialApplication.class})
public class QRCodeTests {

    private static long id = 1;



    @Autowired
    private QRCodeService  qrCodeService;

    @BeforeClass
    public static void init(){

    }

    @Test
    public void testGoodsQRCode(){
        CreateGoodsQRCode createGoodsQRCode = new CreateGoodsQRCode();
        createGoodsQRCode.setMaterialUrl("item.jd.com/2242675.html");
        createGoodsQRCode.setSize(72);

        Base64QRCode code = qrCodeService.createQRCodeForGoods(1230061311020957698L, createGoodsQRCode);
        byte[] raw = Base64.decodeBase64(code.getContent());
        File file = new File("qrcode.png");
        if(file.exists()){
            file.delete();
        }
        try {

            BufferedImage image = ImageIO.read(new ByteInputStream(raw, raw.length));     //将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            log.error("", e);
        }
        log.debug("qr code {}", file.getAbsoluteFile());
        Assert.assertTrue(file.exists());

    }
}
