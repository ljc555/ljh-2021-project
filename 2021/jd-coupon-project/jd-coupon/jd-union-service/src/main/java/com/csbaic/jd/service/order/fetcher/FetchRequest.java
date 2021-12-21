package com.csbaic.jd.service.order.fetcher;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FetchRequest {

    /**
     * 订单时间查询类型(1：下单时间，2：完成时间，3：更新时间)
     */
    private Integer type;

    /**
     * 查询时间
     */
    private LocalDateTime begin;

    /**
     * 查询时间
     */
    private LocalDateTime end;


    /**
     * 页记录
     */
    private Integer pageIndex;

    /**
     * 分页大小
     */
    private Integer pageSize;
 
}
