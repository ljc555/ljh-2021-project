package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SeckillInfo {

    /***
     * 秒杀价原价
     */
    @ApiModelProperty("秒杀价原价")
    private Double seckillOriPrice;
    /**
     * 秒杀价
     */
    @ApiModelProperty("秒杀价")
    private Double seckillPrice;
    /**
     * 秒杀开始时间(时间戳，毫秒)
     */
    @ApiModelProperty("秒杀开始时间(时间戳，毫秒)")
    private Long seckillStartTime;
    /**
     * 秒杀结束时间(时间戳，毫秒)
     */
    @ApiModelProperty("秒杀结束时间(时间戳，毫秒)")
    private Long seckillEndTime;
}
