package com.csbaic.jd.commission;

import com.csbaic.jd.enums.UserIdentify;
import com.csbaic.jd.service.goods.detail.GoodsDetail;
import com.csbaic.jd.service.goods.detail.GoodsDetailRepository;
import com.csbaic.jd.service.order.handle.OrderMetadata;
import com.csbaic.jd.service.order.handle.filter.MemberCommissionSaver;
import com.csbaic.jd.service.team.MemberContainer;
import com.csbaic.jd.service.team.MemberContainerBuilder;
import com.csbaic.jd.service.team.bean.MemberNode;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yjwfn on 2020/2/16.
 */
@Configuration
public class CommissionConfiguration {


    @Primary
    @Bean
    public MemberCommissionSaver memberCommissionSaver(){
        final Logger log = LoggerFactory.getLogger(MemberCommissionSaver.class.getName());
        return new MemberCommissionSaver() {
            @Override
            public void save(MemberContainer memberContainer, OrderMetadata orderMetadata, OrderMetadata.SkuMetadata skuMetadata) {
                MemberNode node = memberContainer.getRoot();
                while (node != null){
                    log.info("Commission {}", node);
                    node = node.getNext();
                }
            }
        };
    }


    @Bean
    @Primary
    public GoodsDetailRepository goodsDetailRepository(){
        return new GoodsDetailRepository() {
            @Override
            public List<GoodsDetail> getGoodsDetail(Long... skuIds) {
                return new ArrayList<>();
            }
        };
    }

    @Bean
    @Primary
    public MemberContainerBuilder containerBuilder(){
        List<MemberNode> nodeList = new ArrayList<>();
        MemberNode memberNode = new MemberNode();
        memberNode.setIdentify(UserIdentify.REGISTERED);
        memberNode.setUserId(1L);
        memberNode.setLevel(0);
        nodeList.add(memberNode);


        memberNode = new MemberNode();
        memberNode.setIdentify(UserIdentify.SUPER);
        memberNode.setUserId(2L);
        memberNode.setLevel(1);
        nodeList.add(memberNode);


        memberNode = new MemberNode();
        memberNode.setIdentify(UserIdentify.SUPER);
        memberNode.setUserId(3L);
        memberNode.setLevel(2);
        nodeList.add(memberNode);

        memberNode = new MemberNode();
        memberNode.setIdentify(UserIdentify.SUPER);
        memberNode.setUserId(4L);
        memberNode.setLevel(3);
        nodeList.add(memberNode);

        memberNode = new MemberNode();
        memberNode.setIdentify(UserIdentify.SUPER);
        memberNode.setUserId(5L);
        memberNode.setLevel(4);
        nodeList.add(memberNode);


        memberNode = new MemberNode();
        memberNode.setIdentify(UserIdentify.TEACHER);
        memberNode.setUserId(6L);
        memberNode.setLevel(5);
        nodeList.add(memberNode);

        memberNode = new MemberNode();
        memberNode.setIdentify(UserIdentify.TEACHER);
        memberNode.setUserId(7L);
        memberNode.setLevel(6);
        nodeList.add(memberNode);

        memberNode = new MemberNode();
        memberNode.setIdentify(UserIdentify.TEACHER);
        memberNode.setUserId(8L);
        memberNode.setLevel(7);
        nodeList.add(memberNode);


        return new MockMemberContainerBuilder(nodeList);
    }
}
