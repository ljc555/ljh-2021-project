package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("分页查询")
public class PageQuery {

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

}
