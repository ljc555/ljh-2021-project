package com.csbaic.jd.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.common.result.ResultCode;
import com.csbaic.jd.entity.WalletEntity;
import com.csbaic.jd.enums.WalletStatus;
import com.csbaic.jd.mapper.WalletMapper;
import com.csbaic.jd.service.IWalletService;
import com.csbaic.jd.service.settlement.BigDecimalPrecisionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-22
 */
@Service
public class WalletServiceImpl extends ServiceImpl<WalletMapper, WalletEntity> implements IWalletService {

    /**
     * 钱包操作锁
     */
    private static final ReadWriteLock LOCK = new ReentrantReadWriteLock();

    @Autowired
    private BigDecimalPrecisionStrategy bigDecimalPrecisionStrategy;

    @Override
    public BigDecimal getBalanceWithoutFreeze(Long userId) {

        BigDecimal balance = BigDecimal.ZERO;
        Lock readLock = LOCK.readLock();
        try{
            readLock.lock();
            WalletEntity walletEntity  = getOne(
                    Wrappers.<WalletEntity>query()
                            .eq(WalletEntity.USER_ID, userId)
            );

            if(walletEntity == null || walletEntity.getBalance() == null){
               return BigDecimal.ZERO;
            }

            //减去已被冻结的金额
            if(walletEntity.getBalanceFreeze() != null){
                balance = walletEntity.getBalance().subtract(walletEntity.getBalanceFreeze());
            }

        }finally {
            readLock.unlock();
        }
        return balance;
    }

    @Override
    public void addBalance(Long userId, BigDecimal balance) {
        if(balance == null || balance.compareTo(BigDecimal.ZERO) <= 0){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "增加金额不正确");
        }

        Lock writeLock = LOCK.writeLock();
        try{
            writeLock.lock();
            WalletEntity walletEntity  = getOne(
                    Wrappers.<WalletEntity>query()
                            .eq(WalletEntity.USER_ID, userId)
            );

            if(walletEntity == null){
                walletEntity = new WalletEntity();
                walletEntity.setUserId(userId);
                walletEntity.setBalance(BigDecimal.ZERO);
                walletEntity.setStatus(WalletStatus.ENABLE.ordinal());
            }

            balance = balance.add(walletEntity.getBalance());
            walletEntity.setBalance(balance);
            saveOrUpdate(walletEntity);
        }finally {
            writeLock.unlock();
        }
    }

    @Override
    public void subBalance(Long userId, BigDecimal balance) {
        if(balance == null || balance.compareTo(BigDecimal.ZERO) <= 0){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "减少金额不正确");
        }

        Lock writeLock = LOCK.writeLock();
        try{
            writeLock.lock();
            WalletEntity walletEntity  = getOne(
                    Wrappers.<WalletEntity>query()
                            .eq(WalletEntity.USER_ID, userId)
            );

            if(walletEntity == null){
                throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "钱包余额不足");
            }

            BigDecimal balanceRemaining = getBalanceWithoutFreeze(userId);
            if(balanceRemaining.compareTo(balance) < 0){
                throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "钱包余额不足");
            }
            walletEntity.setBalance(balanceRemaining.subtract(balance));
            saveOrUpdate(walletEntity);
        }finally {
            writeLock.unlock();
        }
    }

    @Override
    public void lockBalance(Long userId, BigDecimal balance) {
        if(balance == null || balance.compareTo(BigDecimal.ZERO) <= 0){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "减少金额不正确");
        }


        balance = bigDecimalPrecisionStrategy.apply(balance);
        Lock writeLock = LOCK.writeLock();
        try{
            writeLock.lock();
            WalletEntity walletEntity  = getOne(
                    Wrappers.<WalletEntity>query()
                            .eq(WalletEntity.USER_ID, userId)
            );

            if(walletEntity == null){
                throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "钱包余额不足");
            }

            BigDecimal balanceRemaining = getBalanceWithoutFreeze(userId);
            if(balanceRemaining.compareTo(balance) < 0){
                throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "钱包余额不足");
            }

            if(balanceRemaining.compareTo(balance) < 0){
                throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "钱包余额不足，无法锁定金额");
            }

            BigDecimal lockedBalance = walletEntity.getBalanceFreeze() == null ? BigDecimal.ZERO : walletEntity.getBalanceFreeze();
            lockedBalance = lockedBalance.add(balance);
            walletEntity.setBalanceFreeze(lockedBalance);
            saveOrUpdate(walletEntity);
        }finally {
            writeLock.unlock();
        }
    }

    @Override
    public void unlockBalance(Long userId, BigDecimal balance) {

        if(balance == null || balance.compareTo(BigDecimal.ZERO) <= 0){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "金额不正确");
        }


        balance = bigDecimalPrecisionStrategy.apply(balance);
        Lock writeLock = LOCK.writeLock();
        try{
            writeLock.lock();
            WalletEntity walletEntity  = getOne(
                    Wrappers.<WalletEntity>query()
                            .eq(WalletEntity.USER_ID, userId)
            );

            if(walletEntity == null){
                throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "还没有生成钱包");
            }


            BigDecimal lockedBalance = walletEntity.getBalanceFreeze() == null ? BigDecimal.ZERO : walletEntity.getBalanceFreeze();
            lockedBalance = lockedBalance.add(balance.negate());

            if(lockedBalance.compareTo(BigDecimal.ZERO) < 0){
                throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "解冻金额错误");
            }

            walletEntity.setBalanceFreeze(lockedBalance);
            saveOrUpdate(walletEntity);
        }finally {
            writeLock.unlock();
        }

    }
}
