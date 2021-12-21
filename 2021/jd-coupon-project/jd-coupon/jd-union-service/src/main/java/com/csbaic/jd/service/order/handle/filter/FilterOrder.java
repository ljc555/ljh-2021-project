package com.csbaic.jd.service.order.handle.filter;

import org.springframework.core.Ordered;

/**
 * Created by yjwfn on 2020/2/15.
 */
public interface FilterOrder {



    /**
     * 保存同步记录
     */
    int ORDER_SYNC_STATUS_RECORDER = Ordered.HIGHEST_PRECEDENCE;

    /**
     * 订单调整
     */
    int ORDER_SYNC_ORDER_ADJUST = Ordered.HIGHEST_PRECEDENCE + 1;

    /**
     * 保存订单顺序
     */
    int ORDER_SAVE_FILTER = ORDER_SYNC_ORDER_ADJUST + 1;

    /**
     * 佣金计算
     */
    int ORDER_MEMBER_IDENTIFY_COMMISSION_FILTER = ORDER_SAVE_FILTER + 1 ;

}
