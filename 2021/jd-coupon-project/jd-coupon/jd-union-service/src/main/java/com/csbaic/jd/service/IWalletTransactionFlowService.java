package com.csbaic.jd.service;

import com.csbaic.jd.dto.SettlementTransactionFlow;
import com.csbaic.jd.dto.WithdrawTransactionFlow;
import com.csbaic.jd.entity.WalletTransactionFlowEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 保存商品每天的同步时间 服务类
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-20
 */
public interface IWalletTransactionFlowService extends IService<WalletTransactionFlowEntity> {


    /**
     * 创建结算交易流水
     * @param transactionFlow
     */
    void createTransactionFlow(SettlementTransactionFlow transactionFlow);


    /**
     * 创建提现流水
     * @param transactionFlow
     */
    void createTransactionFlow(WithdrawTransactionFlow transactionFlow);
}
