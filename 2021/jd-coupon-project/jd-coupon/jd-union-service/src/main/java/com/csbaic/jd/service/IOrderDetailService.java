package com.csbaic.jd.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.dto.OrderInfo;
import com.csbaic.jd.dto.QueryOrder;
import com.csbaic.jd.entity.OrderDetailEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 订单记录 服务类
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-14
 */
public interface IOrderDetailService extends IService<OrderDetailEntity> {


    /**
     * 按时间维度查询订单
     * @param queryOrder
     * @return
     */
    IPage<OrderInfo> queryOrderByTime(Long userId, QueryOrder queryOrder);


    /**
     * 获取购买订单量
     * @param begin
     * @param end
     * @return
     */
    int getCountWithBuyerBetween(Long buyerId, LocalDateTime begin , LocalDateTime end);

    /**
     * 获取购买订单量
     * @param begin
     * @param end
     * @return
     */
    int getCountWithOwnerBetween(Long ownerId, LocalDateTime begin , LocalDateTime end);


    /**
     *
     * @return
     */
    List<Long> findUsersByFinishedOrder(LocalDateTime begin , LocalDateTime end );
}
