package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 新闻
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-14
 */
@Data
public class CreateNews implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 活动标题
     */
    @ApiModelProperty("活动标题")
    private String title;



    /**
     * 跳转页面
     */
    @ApiModelProperty("快报跳转路径")
    private String location;



    /**
     * 活动开始时间
     */
    @ApiModelProperty(value = "快报开始时间（不传为当前时间）", example = "20200302120000")
    private String startTime;

    /**
     * 活动结束时间
     */
    @ApiModelProperty(value = "快报结束时间（不传为当前时间 + 1年）", example = "20200402120000")
    private String endTime;


    @ApiModelProperty(value = "快报状态", example = "1（自动）,2（显示）,3（隐藏）", allowableValues = "1,2,3")
    private Integer status;

}
