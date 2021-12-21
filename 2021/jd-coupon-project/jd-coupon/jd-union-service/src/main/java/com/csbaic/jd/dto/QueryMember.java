package com.csbaic.jd.dto;

import lombok.Data;

@Data
public class QueryMember {

    /**
     * 分页
     */
    private Integer pageIndex;

    /**
     * 分页大小
     */
    private Integer pageSize;

    /**
     * 用户身份
     */
    private Integer identify;

}
