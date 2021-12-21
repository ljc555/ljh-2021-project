package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("我的粉丝")
@Data
public class MemberFansInfo {


    /**
     * 今日新增数量
     */
    @ApiModelProperty("今日新增粉丝")
    private Integer countForToday;

    /**
     * 所有数据
     */
    @ApiModelProperty("所有粉丝")
    private Integer totalCount;


}
