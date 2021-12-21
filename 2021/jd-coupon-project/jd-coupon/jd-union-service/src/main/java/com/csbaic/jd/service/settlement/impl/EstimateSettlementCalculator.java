package com.csbaic.jd.service.settlement.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.dto.MergedMemberCommission;
import com.csbaic.jd.service.IMemberCommissionService;
import com.csbaic.jd.service.settlement.AbstractMemberBillingCalculator;
import com.csbaic.jd.service.settlement.CalculateRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.csbaic.jd.service.settlement.BillingCalculators.ESTIMATE_SETTLEMENT;

/**
 * 计算预估结算收益
 */
@Qualifier(ESTIMATE_SETTLEMENT)
@Component
public class EstimateSettlementCalculator extends AbstractMemberBillingCalculator {

    private final IMemberCommissionService commissionService;

    public EstimateSettlementCalculator(IMemberCommissionService commissionService) {
        this.commissionService = commissionService;
    }

    @Override
    protected IPage<MergedMemberCommission> getMemberCommission(IPage<MergedMemberCommission> page, CalculateRequest request) {
        return commissionService.getBillingForUserIdBetweenFinishTimeAndValidCode(page, request.getUserId(), request.getStart(), request.getEnd(), null);
    }
}
