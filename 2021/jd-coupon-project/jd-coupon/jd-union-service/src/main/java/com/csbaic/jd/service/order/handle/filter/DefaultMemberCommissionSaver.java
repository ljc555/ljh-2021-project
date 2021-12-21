package com.csbaic.jd.service.order.handle.filter;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.csbaic.jd.entity.MemberCommissionEntity;
import com.csbaic.jd.service.IMemberCommissionService;
import com.csbaic.jd.service.commission.MemberCommissionInfo;
import com.csbaic.jd.service.order.handle.OrderMetadata;
import com.csbaic.jd.service.settlement.BigDecimalPrecisionStrategy;
import com.csbaic.jd.service.team.MemberContainer;
import com.csbaic.jd.service.team.bean.MemberNode;
import com.csbaic.jd.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yjwfn on 2020/2/15.
 */
@Slf4j
@Component
public class DefaultMemberCommissionSaver implements MemberCommissionSaver {

    /**
     * 佣金服务
     */
    private final IMemberCommissionService commissionService;



    private final BigDecimalPrecisionStrategy precisionStrategy;



    @Autowired
    public DefaultMemberCommissionSaver(IMemberCommissionService commissionService, BigDecimalPrecisionStrategy precisionStrategy) {
        this.commissionService = commissionService;

        this.precisionStrategy = precisionStrategy;
    }

    @Transactional
    @Override
    public void save(MemberContainer memberContainer, OrderMetadata orderMetadata, OrderMetadata.SkuMetadata skuMetadata) {

        MemberNode node = memberContainer.getRoot();


        /*
            删除老的记录，防止更新过程中用户升级后计算不正确问题
         */

        commissionService.remove(
                Wrappers.<MemberCommissionEntity>query()
                .eq(MemberCommissionEntity.ORDER_ID, orderMetadata.getOrderId())
                .eq(MemberCommissionEntity.SKU_ID, skuMetadata.getSkuId())
        );

        while (node != null){
            saveMemberCommissionInfo(node, orderMetadata, skuMetadata);
            log.info("Commission {}", node);
            node = node.getNext();
        }
    }


    private void saveMemberCommissionInfo(MemberNode memberNode, OrderMetadata orderMetadata, OrderMetadata.SkuMetadata skuMetadata){

        List<MemberCommissionInfo> commissionInfo =  memberNode.getCommissions();
        if (commissionInfo == null || commissionInfo.isEmpty()) {
            return;
        }

        Long batchId = IdWorker.getId();
        List<MemberCommissionEntity> commissionEntities = new ArrayList<>();
        for(MemberCommissionInfo info : commissionInfo){

            MemberCommissionEntity entity = new MemberCommissionEntity();
            entity.setBatchId(batchId);

            Long orderTime = orderMetadata.getOrderTime();
            Long finishTime = orderMetadata.getFinishTime();

            entity.setOrderId(orderMetadata.getOrderId());
            entity.setOrderTime(orderTime != null && orderTime > 0 ? TimeUtils.toLocalDateTime(orderTime) : null);
            entity.setFinishTime(finishTime != null && finishTime > 0 ? TimeUtils.toLocalDateTime(finishTime) : null);
            entity.setSkuId(skuMetadata.getSkuId());
            entity.setSkuName(skuMetadata.getSkuName());
            entity.setUserId(memberNode.getUserId());
            entity.setIdentify(memberNode.getIdentify().getCode());
            entity.setValidCode(skuMetadata.getValidCode());
            entity.setEstimateRebateFee(precisionStrategy.apply(info.getEstimateRebateFee()));
            entity.setActualRebateFee(precisionStrategy.apply(info.getActualRebateFee()));
            entity.setEstimateAwardFee(precisionStrategy.apply(info.getEstimateAwardFee()));
            entity.setActualAwardFee(precisionStrategy.apply(info.getActualAwardFee()));
            entity.setEstimateCommissionFee(precisionStrategy.apply(info.getEstimateCommissionFee()));
            entity.setActualCommissionFee(precisionStrategy.apply(info.getActualCommissionFee()));
            entity.setRemark(info.getRemark());
            entity.setLevel(memberNode.getLevel());
            entity.setFeeType(info.getFeeType());
            commissionEntities.add(entity);
        }

        if(!commissionEntities.isEmpty()){
            commissionService.saveBatch(commissionEntities);
        }

    }


}
