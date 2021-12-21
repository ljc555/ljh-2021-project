package com.csbaic.jd.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Banner {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /***
     * Banner跳转位置
     */
    private String location;


    /**
     * Banner封面图片地址
     */
    private String coverUrl;


    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;


    /**
     * 是否启用
     */
    private Integer status;



}
