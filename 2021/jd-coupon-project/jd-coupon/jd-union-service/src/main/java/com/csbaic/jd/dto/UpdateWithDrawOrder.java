package com.csbaic.jd.dto;

import lombok.Data;

@Data
public class UpdateWithDrawOrder {

    /**
     * 申请单id
     */
    private Long id;

    /**
     * 申请状态
     */
    private Integer status;
}
