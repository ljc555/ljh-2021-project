package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PinGouInfo {

    /**
     *
     * 拼购价格
     */
    @ApiModelProperty("拼购价格")
    private Double pingouPrice;
    /**
     *
     * 拼购成团所需人数
     */
    @ApiModelProperty("拼购成团所需人数")
    private Long pingouTmCount;
    /**
     * 拼购落地页url
     */
    @ApiModelProperty("拼购开始时间(时间戳，毫秒)")
    private String pingouUrl;
    /**
     * 拼购开始时间(时间戳，毫秒)
     */
    @ApiModelProperty("拼购价格")
    private Long pingouStartTime;
    /**
     * 拼购结束时间(时间戳，毫秒)
     */
    @ApiModelProperty("拼购结束时间(时间戳，毫秒)")
    private Long pingouEndTime;



}
