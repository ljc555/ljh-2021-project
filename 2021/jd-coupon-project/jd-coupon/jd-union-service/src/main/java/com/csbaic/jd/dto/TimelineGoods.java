package com.csbaic.jd.dto;

import lombok.Data;

import java.time.LocalDateTime;



@Data
public class TimelineGoods {

    private Long id;

    /**
     * 活动标题
     */
    private String title;


    /**
     * 京东商品id
     */
    private Long skuId;


    /**
     * 发布人信息
     */
    private PublisherInfo publisherInfo;


    /**
     * 活动图片
     */
    private String imageUrl;

    /**
     * 活动内容
     */
    private String content;


    /**
     * 创建时间
     */
    private LocalDateTime createTime;



}
