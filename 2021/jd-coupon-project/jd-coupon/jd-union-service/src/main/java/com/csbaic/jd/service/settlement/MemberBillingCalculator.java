package com.csbaic.jd.service.settlement;

/**
 * 佣金结算计算器
 */
public interface MemberBillingCalculator {


    /**
     * 计算成员账单
     * @param request
     * @return
     */
    MemberBilling cal(CalculateRequest request);


}
