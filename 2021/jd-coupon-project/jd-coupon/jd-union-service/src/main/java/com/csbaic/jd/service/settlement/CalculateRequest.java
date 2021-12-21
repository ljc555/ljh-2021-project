package com.csbaic.jd.service.settlement;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 计算账单参数
 */
@Data
@ToString
public class CalculateRequest {

    /**
     * 开始时间
     */
    private final LocalDateTime start;

    /**
     * 结束时间
     */
    private final LocalDateTime end;

    /**
     * 用户id
     */
    private final Long userId;


    
}
