package com.csbaic.jd.service.order.handle.filter;

import com.csbaic.jd.service.commission.CommissionStrategy;
import com.csbaic.jd.service.order.SubUnionId;
import com.csbaic.jd.service.order.SubUnionIdConverter;
import com.csbaic.jd.service.order.handle.OrderHandlerChain;
import com.csbaic.jd.service.order.handle.OrderMetadata;
import com.csbaic.jd.service.team.MemberContainer;
import com.csbaic.jd.service.team.MemberContainerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yjwfn on 2020/2/15.
 */
@Component
@Order(FilterOrder.ORDER_MEMBER_IDENTIFY_COMMISSION_FILTER)
public class OrderCommissionFilter implements OrderHandlerChain.OrderFilter {

    private final MemberCommissionSaver memberCommissionSaver;

    private final MemberContainerBuilder memberContainerBuilder;

    private final CommissionStrategy commissionStrategy;

    private final SubUnionIdConverter subUnionIdConverter;

    @Autowired
    public OrderCommissionFilter(MemberCommissionSaver memberCommissionSaver, MemberContainerBuilder memberContainerBuilder, CommissionStrategy commissionStrategy, SubUnionIdConverter subUnionIdResolver) {
        this.memberCommissionSaver = memberCommissionSaver;
        this.memberContainerBuilder = memberContainerBuilder;
        this.commissionStrategy = commissionStrategy;
        this.subUnionIdConverter = subUnionIdResolver;
    }

    /**
     * 默认会员 {@link com.csbaic.jd.enums.UserGrade#DEFAULT}，购买商品自己可以获取50%的佣金
     * 上线无佣金 ，防止推广后不管理问题，上线一定要成为超级会员才能享受下线佣金 。
     * @param chain
     */
    @Override
    public void filter(OrderHandlerChain chain) {

        /*
            计算sku佣金
         */
        List<OrderMetadata.SkuMetadata> skuMetadata = chain.getOrderMetadata().getSkuMetadata();
        OrderMetadata orderMetadata = chain.getOrderMetadata();

        for (OrderMetadata.SkuMetadata metadata : skuMetadata) {
            //计算佣金
            MemberContainer container = commissionStrategy.cal(orderMetadata, metadata);
            //保存佣金信息
            memberCommissionSaver.save(container, orderMetadata, metadata);
        }

        chain.handle(orderMetadata);
    }



}
