package com.csbaic.jd.commission;

import com.csbaic.jd.JdSocialApplication;
import com.csbaic.jd.dto.GoodsOrder;
import com.csbaic.jd.dto.SkuInfo;
import com.csbaic.jd.enums.OrderStatus;
import com.csbaic.jd.enums.UserIdentify;
import com.csbaic.jd.service.commission.CommissionStrategy;
import com.csbaic.jd.service.commission.MemberCommissionInfo;
import com.csbaic.jd.service.order.SubUnionId;
import com.csbaic.jd.service.order.SubUnionIdConverter;
import com.csbaic.jd.service.order.handle.OrderHandlerFactory;
import com.csbaic.jd.service.order.handle.OrderMetadata;
import com.csbaic.jd.service.order.handle.filter.MemberCommissionSaver;
import com.csbaic.jd.service.team.MemberContainer;
import com.csbaic.jd.service.team.bean.MemberNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yjwfn on 2020/2/16.
 */
@ActiveProfiles(value = "local")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JdSocialApplication.class, CommissionConfiguration.class})
public class CommissionTests {

    private static long id = 1;

    @Autowired
    private SubUnionIdConverter subUnionIdConverter;

    @Autowired
    private OrderHandlerFactory orderHandlerFactory;

    @BeforeClass
    public static void init() {

    }


    @Test
    public void testRebate() {
        SubUnionId subUnionId = new SubUnionId();
        subUnionId.setBuyer(1L);
        subUnionId.setIdentifyOfBuyer(UserIdentify.SUPER.getCode());
        subUnionId.setOwner(3L);
        subUnionId.setIdentifyOfOwner(UserIdentify.SUPER.getCode());
        subUnionId.setRebate(true);

        OrderMetadata orderMetadata = makeOrder(makeSku(subUnionIdConverter.covert(subUnionId)));
        orderHandlerFactory.create().handle(orderMetadata);
    }

    private static SkuInfo makeSku(String subUnionId) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setSubUnionId(subUnionId);
        skuInfo.setEstimateFee(100D);
        skuInfo.setActualFee(100D);
        skuInfo.setActualCosPrice(100D);
        skuInfo.setEstimateCosPrice(100D);
        skuInfo.setCommissionRate(100D);
        return skuInfo;
    }

    private static GoodsOrder makeOrder(SkuInfo... skuMetadata) {

        GoodsOrder order = new GoodsOrder();
        order.setOrderId(System.currentTimeMillis());
        order.setFinishTime(System.currentTimeMillis());
        order.setOrderTime(System.currentTimeMillis());
        order.setValidCode(OrderStatus.FINISHED.getCode());
        order.setSkuList(skuMetadata);

        return order;
    }
}

