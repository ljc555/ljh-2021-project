package com.csbaic.jd.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.dto.MergedMemberCommission;
import com.csbaic.jd.entity.MemberCommissionEntity;
import com.csbaic.jd.mapper.MemberCommissionMapper;
import com.csbaic.jd.service.IMemberCommissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csbaic.jd.service.settlement.BillingCalculators;
import com.csbaic.jd.service.settlement.CalculateRequest;
import com.csbaic.jd.service.settlement.MemberBilling;
import com.csbaic.jd.service.settlement.MemberBillingCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 佣金商品记录 服务实现类
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-15
 */
@Service
public class MemberCommissionServiceImpl extends ServiceImpl<MemberCommissionMapper, MemberCommissionEntity> implements IMemberCommissionService {

    @Autowired
    @Qualifier(BillingCalculators.ESTIMATE_FEE)
    private MemberBillingCalculator estimateFeeCalculator;


    @Autowired
    @Qualifier(BillingCalculators.ACTUAL_SETTLEMENT)
    private MemberBillingCalculator actualSettlementCalculator;


    @Override
    public MergedMemberCommission getMergedOrderSkuFee(Long userId, Long orderId, Long skuId) {
        return getBaseMapper().getMergedOrderSkuFee(userId, orderId,skuId);
    }

    @Override
    public MergedMemberCommission getRebateFeeFor30Days(Long userId) {
        return getBaseMapper().getRebateFeeFor30Days(userId);
    }

    @Override
    public IPage<MergedMemberCommission> getRebateFeeFor30Days(IPage<MergedMemberCommission> page) {
        return getBaseMapper().getAllRebateFeeFor30Days(page);
    }

    @Override
    public IPage<MergedMemberCommission> getBillingForUserIdBetweenFinishTimeAndValidCode(IPage<MergedMemberCommission> page, Long userId, LocalDateTime start, LocalDateTime end, Integer validCode) {
        return getBaseMapper().getBillingForUserIdBetweenFinishTimeAndValidCode(page, userId, start, end, validCode);
    }

    @Override
    public IPage<MergedMemberCommission> getBillingForUserIdBetweenOrderTimeAndValidCode(IPage<MergedMemberCommission> page, Long userId, LocalDateTime start, LocalDateTime end, Integer validCode) {
        return getBaseMapper().getBillingForUserIdBetweenOrderTimeAndValidCode(page,userId, start, end, validCode);
    }

    @Override
    public BigDecimal getEstimateRebateFeeBetween(Long userId, LocalDateTime begin, LocalDateTime end) {
        return getEstimateFeeBetween(userId, begin, end).getEstimateRebateFee();
    }

    @Override
    public BigDecimal getEstimateAwardFeeBetween(Long userId, LocalDateTime begin, LocalDateTime end) {
        return getEstimateFeeBetween(userId, begin, end).getEstimateAwardFee();
    }

    @Override
    public BigDecimal getEstimateCommissionFeeBetween(Long userId, LocalDateTime begin, LocalDateTime end) {
        return getEstimateFeeBetween(userId, begin, end).getEstimateCommissionFee();
    }

    @Override
    public BigDecimal getTotalEstimateFeeBetween(Long userId, LocalDateTime begin, LocalDateTime end) {
        CalculateRequest calculateRequest = new CalculateRequest(begin, end , userId);

        MemberBilling memberBilling =  estimateFeeCalculator.cal(calculateRequest);
        BigDecimal totalFee  = BigDecimal.ZERO;
        totalFee = totalFee.add(memberBilling.getEstimateCommissionFee());
        totalFee = totalFee.add(memberBilling.getEstimateRebateFee());
        totalFee = totalFee.add(memberBilling.getEstimateAwardFee());

        return totalFee;
    }

    @Override
    public MemberBilling getEstimateFeeBetween(Long userId, LocalDateTime begin, LocalDateTime end) {
        CalculateRequest calculateRequest = new CalculateRequest(begin, end , userId);
        return estimateFeeCalculator.cal(calculateRequest);
    }

    @Override
    public MemberBilling getEstimateSettlementFee(Long userId, LocalDateTime begin, LocalDateTime end) {
        CalculateRequest calculateRequest = new CalculateRequest(begin, end , userId);
        return actualSettlementCalculator.cal(calculateRequest);
    }

}
