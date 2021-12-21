package com.csbaic.jd.dto;

import lombok.Data;

@Data
public class CreateSharePoster  {

    /**
     * 海报名称
     */
    private String title;

    /**
     * 图片Url
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
