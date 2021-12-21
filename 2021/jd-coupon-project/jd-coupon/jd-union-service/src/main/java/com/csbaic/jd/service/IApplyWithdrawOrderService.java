package com.csbaic.jd.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.dto.ApplyWithdrawInfo;
import com.csbaic.jd.dto.UpdateWithDrawOrder;
import com.csbaic.jd.dto.WithdrawAccountInfo;
import com.csbaic.jd.dto.WithdrawOrderInfo;
import com.csbaic.jd.entity.ApplyWithdrawOrderEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 提现申请单 服务类
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-22
 */
public interface IApplyWithdrawOrderService extends IService<ApplyWithdrawOrderEntity> {

    /**
     * 获取提现账户信息
     * @param userId
     * @return
     */
    WithdrawAccountInfo getWithdrawAccountInfo(Long userId);

    /**
     * 创建申请提现单
     * @param withdrawInfo
     */
    void createApplyWithdrawOrder(Long userId, ApplyWithdrawInfo withdrawInfo);


    /**
     * 取消申请
     * @param
     */
    void cancelApplyWithdrawOrder(Long userId, Long oid);

    /**
     * 更新提示单
     * @param userId
     * @param withdrawInfo
     */
    void updateApplyWithdrawOrder(Long userId, UpdateWithDrawOrder withdrawInfo);



    /**
     * 获取系统中的的提现单
     * @param
     * @return
     */
    IPage<WithdrawOrderInfo> getWithdrawOrders(Integer pageIndex, Integer pageSize, Integer type);


    /**
     * 获取用户的提现单
     * @param userId
     * @return
     */
    IPage<WithdrawOrderInfo> getWithdrawOrders(Long userId, Integer pageIndex, Integer pageSize);


    /**
     * 支付提现申请
     * @param withdrawId
     */
    void payForWithdraw(Long operatorId, Long withdrawId);

}
