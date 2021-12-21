package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel
@Data
public class CreateActivity {

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
    @ApiModelProperty(value = "活动开始时间（不传为当前时间）", example = "20200302120000")
    private String startTime;

    /**
     * 活动结束时间
     */
    @ApiModelProperty(value = "活动结束时间（如果不传为当前时间+1年", example = "20200402120000")
    private String endTime;




    /**
     * 活动状态
     */
    @ApiModelProperty(value = "状态（1: 自动，2：显示，3：未显示）")
    private Integer status;





}
