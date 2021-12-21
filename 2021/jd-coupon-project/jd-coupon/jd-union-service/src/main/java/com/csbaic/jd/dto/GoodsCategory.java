package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class GoodsCategory {

    /**
     * 分类id
     */
    @ApiModelProperty("分类id")
    private Integer id;
    /**
     * 分类名称
     */
    @ApiModelProperty("分类名称")
    private String name;
    /**
     * 类目级别(类目级别 0，1，2 代表一、二、三级类目)
     */
    @ApiModelProperty("类目级别(类目级别 0，1，2 代表一、二、三级类目)")
    private Integer grade;

    /***
     * 父类目Id
     */
    @ApiModelProperty("父类目Id")
    private Integer parentId;

}
