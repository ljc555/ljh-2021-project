package com.csbaic.jd.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


@ApiModel
@Data
public class Activity {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 活动标题
     */
    @ApiModelProperty("活动标题")
    private String title;

    /**
     * 活动分享图
     */
    @ApiModelProperty("活动分享图")
    private String shareImage;

    /**
     * 活动logo图
     */
    @ApiModelProperty("活动logo图")
    private String logoImage;
    /**
     * 活动分享文本
     */
    @ApiModelProperty("活动分享文本")
    private String content;

    /**
     * 活动开始时间
     */
    @ApiModelProperty("活动开始时间")
    private LocalDateTime startTime;


    /**
     * 活动状态
     */
    @ApiModelProperty("活动状态")
    private Integer status;

    /**
     * 活动结束时间
     */
    @ApiModelProperty("活动结束时间")
    private LocalDateTime endTime;
}
