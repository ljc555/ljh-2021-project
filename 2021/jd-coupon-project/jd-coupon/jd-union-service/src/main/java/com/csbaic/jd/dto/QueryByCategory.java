package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryByCategory {


    /**
     * 页码
     */
    @ApiModelProperty("页码")
    private Integer pageIndex = 1;
    /**
     * 页大小
     */
    @ApiModelProperty("页大小")
    private Integer pageSize = 20;

    @ApiModelProperty("一级分类id")
    private Long cid1;

    @ApiModelProperty("二级分类id")
    private Long cid2;

    @ApiModelProperty("三级分类id")
    private Long cid3;
}
