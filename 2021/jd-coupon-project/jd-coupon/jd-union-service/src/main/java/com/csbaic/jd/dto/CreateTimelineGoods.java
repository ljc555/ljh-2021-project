package com.csbaic.jd.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class CreateTimelineGoods {


    /**
     * 活动标题
     */
    private String title;


    /**
     * 京东商品id
     */
    private Long skuId;


    /**
     * 活动图片
     */
    private String imageUrl;

    /**
     * 活动内容
     */
    private String content;




}
