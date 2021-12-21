package com.csbaic.jd.service.settlement.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.dto.MergedMemberCommission;
import com.csbaic.jd.enums.OrderValidCode;
import com.csbaic.jd.service.IMemberCommissionService;
import com.csbaic.jd.service.settlement.AbstractMemberBillingCalculator;
import com.csbaic.jd.service.settlement.BillingCalculators;
import com.csbaic.jd.service.settlement.CalculateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 计算预估佣金
 */
@Slf4j
@Component
@Qualifier(BillingCalculators.ACTUAL_SETTLEMENT)
public class ActualSettlementCalculator extends AbstractMemberBillingCalculator {

    private final IMemberCommissionService commissionService;

    public ActualSettlementCalculator(IMemberCommissionService commissionService) {
        this.commissionService = commissionService;
    }

    @Override
    protected IPage<MergedMemberCommission> getMemberCommission(IPage<MergedMemberCommission> page, CalculateRequest request) {
        return commissionService.getBillingForUserIdBetweenFinishTimeAndValidCode(page, request.getUserId(), request.getStart(), request.getEnd(), OrderValidCode.FINISHED.getCode());
    }
}
