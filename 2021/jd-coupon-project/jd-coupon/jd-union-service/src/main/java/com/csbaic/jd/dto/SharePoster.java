package com.csbaic.jd.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 分享海报
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-23
 */
@Data
public class SharePoster implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 发布人id
     */
    private PublisherInfo publisherInfo;


    /**
     * 海报名称
     */
    private String title;

    /**
     * 小图标地址
     */
    private String iconUrl;

    /**
     * 分享图
     */
    private String imageUrl;

    /**
     * 海报文字内容
     */
    private String content;


}
