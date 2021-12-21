package com.csbaic.jd.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 新人上手
 * </p>
 *
 * @author yjwfn
 * @since 2020-04-14
 */
@ApiModel
@Data
public class MemberQuickStart implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 记录id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 新人上路标题
     */
    @ApiModelProperty("显示标题")
    private String title;

    /**
     * 新人上路说明
     */
    @ApiModelProperty("显示内容（富文本、图片地址")

    private String content;

    /**
     * 视频地址
     */
    @ApiModelProperty("视频地址")
    private String videoUrl;

    /**
     * 跳转页面、复制、显示图片
     */
    @ApiModelProperty(value = "操作类型（1：跳转页面，2：复制，3：显示图片，4：分享App）", allowableValues = ("1,2,3,4"), example = "1")
    private Integer type;

    /**
     * 显示样式
     */
    @ApiModelProperty(value = "显示样式（1：富文本，2：单图）", allowableValues = ("1,2"), example = "1")
    private Integer style;


    /**
     * 操作提示
     */
    @ApiModelProperty(value = "操作按钮文字，为空则不显示",  example = "去升级")
    private String action;



    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序",  example = "1")
    private Integer sort;

    /**
     * 操作内容
     */
    @ApiModelProperty(value = "根据type的值判断如果是跳转页面就是页面地址，如果是复制就是复制的内容")
    private String data;


}
