package com.csbaic.jd.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

@Data
public class GroupQuestion {

    /**
     * 记录Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 问题分类
     */
    private String category;

    /**
     * 显示图标
     */
    private String icon;

    /**
     * 是否展开
     */
    private Boolean open;


    /***
     * 问题列表
     */
    private List<Question> questions;


}
