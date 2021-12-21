package com.csbaic.jd.service.settlement;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csbaic.jd.dto.MergedMemberCommission;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

/**
 * 周期性的结算
 */
@Slf4j
public abstract class AbstractMemberBillingCalculator implements MemberBillingCalculator {


    @Override
    public MemberBilling cal(CalculateRequest request) {

        if(request == null){
            throw BizRuntimeException.from(ResultCode.ERROR, "参数错误");
        }

        if(request.getUserId() == null){
            throw BizRuntimeException.from(ResultCode.ERROR, "request.userId不能为空");
        }

        if(request.getStart() == null || request.getEnd() == null){
            throw BizRuntimeException.from(ResultCode.ERROR, "必须指定一个结算时间");
        }

        if (request.getEnd().isBefore(request.getStart())) {
            throw BizRuntimeException.from(ResultCode.ERROR, "时间周期不正确");
        }

        log.info("start cal settlement: {}" , request);
        MemberBilling memberBilling = new MemberBilling();
        IPage<MergedMemberCommission> page = getMemberCommission(new Page<>(1, 20), request);
        BigDecimal estimateRebateFee = BigDecimal.ZERO;
        BigDecimal actualRebateFee = BigDecimal.ZERO;
        BigDecimal estimateAwardFee = BigDecimal.ZERO;
        BigDecimal actualAwardFee = BigDecimal.ZERO;
        BigDecimal estimateCommissionFee = BigDecimal.ZERO;
        BigDecimal actualCommissionFee = BigDecimal.ZERO;

        while (page != null && !page.getRecords().isEmpty()){
            log.info("cal page ：pageIndex {} , pageSize {}", page.getCurrent(), page.getSize() );

            MergedMemberCommission merge = cal(page.getRecords());
            estimateRebateFee = estimateRebateFee.add(merge.getEstimateRebateFee());
            estimateAwardFee = estimateAwardFee.add(merge.getEstimateAwardFee());
            actualRebateFee = actualRebateFee.add(merge.getActualRebateFee());
            actualAwardFee = actualAwardFee.add(merge.getActualAwardFee());
            estimateCommissionFee = estimateCommissionFee.add(merge.getEstimateCommissionFee());
            actualCommissionFee = actualCommissionFee.add(merge.getActualCommissionFee());

            page = getMemberCommission(new Page<>(page.getCurrent()  + 1, 20), request);
        }

        memberBilling.setEstimateRebateFee(estimateRebateFee);
        memberBilling.setEstimateAwardFee(estimateAwardFee);
        memberBilling.setActualRebateFee(actualRebateFee);
        memberBilling.setActualAwardFee(actualAwardFee);
        memberBilling.setEstimateCommissionFee(estimateCommissionFee);
        memberBilling.setActualCommissionFee(actualCommissionFee);
        return memberBilling;
    }

    private MergedMemberCommission cal(List<MergedMemberCommission> mergedMemberCommissionList){
        final MergedMemberCommission merged = new MergedMemberCommission();
        mergedMemberCommissionList.forEach(mergedMemberCommission -> merge(merged, mergedMemberCommission));

        return merged;
    }

    private static void merge(MergedMemberCommission from, MergedMemberCommission to){

        BigDecimal estimateRebateFee = from.getEstimateRebateFee() == null ?  BigDecimal.ZERO : from.getEstimateRebateFee();
        BigDecimal estimateAwardFee = from.getEstimateAwardFee() == null ?  BigDecimal.ZERO : from.getEstimateAwardFee();
        BigDecimal actualRebateFee = from.getActualRebateFee() == null ?  BigDecimal.ZERO : from.getActualRebateFee();
        BigDecimal actualAwardFee = from.getActualAwardFee() == null ?  BigDecimal.ZERO : from.getActualAwardFee();
        BigDecimal estimateCommissionFee = from.getEstimateCommissionFee() == null ?  BigDecimal.ZERO : from.getEstimateCommissionFee();
        BigDecimal actualCommissionFee = from.getActualCommissionFee() == null ?  BigDecimal.ZERO : from.getActualCommissionFee();

        from.setEstimateRebateFee(  estimateRebateFee.add(to.getEstimateRebateFee()) );
        from.setActualRebateFee(  actualRebateFee.add(to.getActualRebateFee()) );

        from.setEstimateAwardFee(  estimateAwardFee.add(to.getEstimateAwardFee()) );
        from.setActualAwardFee(  actualAwardFee.add(to.getActualAwardFee()) );

        from.setEstimateCommissionFee(  estimateCommissionFee.add(to.getEstimateCommissionFee()) );
        from.setActualCommissionFee(  actualCommissionFee.add(to.getActualCommissionFee()) );
    }

    /**
     * 获取用户的佣金信息
     * @param page
     * @param request
     * @return
     */
    protected abstract IPage<MergedMemberCommission> getMemberCommission(IPage<MergedMemberCommission> page, CalculateRequest request);

}
