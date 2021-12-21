package com.csbaic.jd.service;

import com.csbaic.jd.entity.WalletEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-22
 */
public interface IWalletService extends IService<WalletEntity> {

    /**
     * 查询钱包余额（不包括已冻结的金额）
     * @param userId
     * @return
     */
    BigDecimal getBalanceWithoutFreeze(Long userId);


    /**
     * 增加用户余额
     * @param userId
     * @param balance
     */
    void addBalance(Long userId, BigDecimal balance);


    /**
     * 减少用户余额
     * @param userId
     * @param balance
     */
    void subBalance(Long userId, BigDecimal balance);


    /**
     * 冻结用户金额
     * @param userId
     * @param balance
     */
    void lockBalance(Long userId, BigDecimal balance);


    /**
     * 解冻用户金额
     * @param userId
     * @param balance
     */
    void unlockBalance(Long userId, BigDecimal balance);

}
