package com.csbaic.jd.service.settlement;

/**
 * 账单（结算）服务
 */
public interface SettlementService {

    /**
     * 月度账单结算
     * @param witch 哪个月
     */
    void settlementMonthly(int witch );


    /**
     * 结算上个月的订单
     * @param
     */
    void settlementForLashMonth();
}
