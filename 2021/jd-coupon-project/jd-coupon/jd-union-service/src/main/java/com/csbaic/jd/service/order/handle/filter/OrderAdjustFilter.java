package com.csbaic.jd.service.order.handle.filter;

import com.csbaic.jd.dto.SkuInfo;
import com.csbaic.jd.enums.UserIdentify;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.jd.service.order.SubUnionId;
import com.csbaic.jd.service.order.SubUnionIdConverter;
import com.csbaic.jd.service.order.handle.OrderHandlerChain;
import com.csbaic.jd.service.order.handle.OrderMetadata;
import com.csbaic.jd.service.team.MemberContainer;
import com.csbaic.jd.service.team.MemberContainerBuilder;

import com.csbaic.jd.service.team.MemberInfo;
import com.csbaic.jd.service.team.bean.MemberNode;
import com.csbaic.jd.utils.UserUtils;
import com.csbaic.common.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Order(FilterOrder.ORDER_SYNC_ORDER_ADJUST)
@Component
public class OrderAdjustFilter implements OrderHandlerChain.OrderFilter {

    private final SubUnionIdConverter converter;

    private final MemberContainerBuilder memberContainerBuilder;

    @Autowired
    public OrderAdjustFilter(SubUnionIdConverter converter, MemberContainerBuilder memberContainerBuilder) {
        this.converter = converter;
        this.memberContainerBuilder = memberContainerBuilder;
    }

    @Override
    public void filter(OrderHandlerChain chain) {

        /*
            调整订单的推广人
            1. 如果推广是注册会员，那订单会订单于上级超级会员或导师

         */
        OrderMetadata orderMetadata = chain.getOrderMetadata();
        List<OrderMetadata.SkuMetadata> skuMetadata = orderMetadata.getSkuMetadata();

        for(OrderMetadata.SkuMetadata sku : skuMetadata){
            adjustOwnerIfNeed(sku);
        }

        chain.handle(chain.getOrderMetadata());
    }

    private void adjustOwnerIfNeed(OrderMetadata.SkuMetadata sku){

        SubUnionId subUnionId = converter.covert(sku.getSubUnionId());


        if(subUnionId.getBuyer() == subUnionId.getOwner()){
            //小程序自购，购买人和推广人都是同一个人。

            UserIdentify identifyOfBuyer = UserUtils.getUserIdentify(subUnionId.getIdentifyOfBuyer());

            if(identifyOfBuyer == UserIdentify.REGISTERED){
                //注册会员自购，订单归购买人的直属超级会员或直属导师

                MemberNode ownerNode = findDirectlyLeader(subUnionId.getBuyer());
                subUnionId.setOwner(ownerNode.getUserId());
                subUnionId.setIdentifyOfOwner(ownerNode.getIdentify().getCode());

            }else if(identifyOfBuyer == UserIdentify.SUPER || identifyOfBuyer == UserIdentify.TEACHER){

                //超级会员或导师自购，订单归自己
                subUnionId.setOwner(subUnionId.getBuyer());
                subUnionId.setIdentifyOfOwner(subUnionId.getIdentifyOfBuyer());
            }

        }else if(subUnionId.getBuyer() == 0){
            //使用他人分享的购买链接，是跟踪不到购买人的，所以当buyer是0是判断为使用购买链接的订单
            UserIdentify identifyOfOwner = UserUtils.getUserIdentify(subUnionId.getIdentifyOfOwner());

            if(identifyOfOwner == UserIdentify.REGISTERED){
                //如果分享人是注册会员，订单会归直属超级会员或者直属导师

                MemberNode ownerNode = findDirectlyLeader(subUnionId.getOwner());
                subUnionId.setOwner(ownerNode.getUserId());
                subUnionId.setIdentifyOfOwner(ownerNode.getIdentify().getCode());
            }

            //分享人为超级会员/分享人为导师不处理使用生成的链接


        }else{
            //使用他人分享的小程序或商品海报购买

            UserIdentify identifyOfOwner = UserUtils.getUserIdentify(subUnionId.getIdentifyOfOwner());
            UserIdentify identifyOfBuyer = UserUtils.getUserIdentify(subUnionId.getIdentifyOfBuyer());


            if(identifyOfBuyer == UserIdentify.REGISTERED){
                //如果推广人是注册会员，订单会归直属超级会员或者直属导师


                if(identifyOfOwner == UserIdentify.REGISTERED){
                    //如果推广也是注册会员，使用购买人的直属领导人
                    MemberNode ownerNode = findDirectlyLeader(subUnionId.getBuyer());
                    subUnionId.setOwner(ownerNode.getUserId());
                    subUnionId.setIdentifyOfOwner(ownerNode.getIdentify().getCode());
                }


            } else if(identifyOfOwner == UserIdentify.SUPER || identifyOfOwner == UserIdentify.TEACHER){
                //如果购买人是超级会员或导师，订单归自己
                subUnionId.setOwner(subUnionId.getBuyer());
                subUnionId.setIdentifyOfOwner(subUnionId.getIdentifyOfBuyer());
            }
        }

        if(sku instanceof SkuInfo){
            ((SkuInfo) sku).setSubUnionId(converter.covert(subUnionId));
        }
    }


    /**
     * 查看直属超级会员或直属导师
     * @param memberId
     * @return
     */
    private MemberNode findDirectlyLeader(Long memberId){

        MemberContainer container = memberContainerBuilder.build(memberId);
        MemberInfo info = container.buildMemberInfo(container.getRoot());

        MemberNode ownerNode = null;

        if(info.getDirectlySuperMember() != null){
            ownerNode = info.getDirectlySuperMember();
        }else if(info.getDirectlyTeacher() != null){
            ownerNode = info.getDirectlyTeacher();
        }else {
            throw BizRuntimeException.from(ResultCode.ERROR, "找不到订单归属人");
        }

        return ownerNode;
    }
}
