package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreateBanner {


    /**
     * 标题
     */
    @ApiModelProperty(value = "banner标题")
    private String title;

    /***
     * Banner跳转位置
     */
    @ApiModelProperty(value = "跳转位置")
    private String location;


    /**
     * Banner封面图片地址
     */
    @ApiModelProperty(value = "封面图")
    private String coverUrl;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "活动开始时间（不传为当前时间）", example = "20200302120000")
    private String startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "活动结束时间（不传为当前时间 + 1年）", example = "20200402120000")
    private String endTime;


    @ApiModelProperty(value = "状态（1: 自动，2：显示，3：未显示）")
    private Integer status;



}
