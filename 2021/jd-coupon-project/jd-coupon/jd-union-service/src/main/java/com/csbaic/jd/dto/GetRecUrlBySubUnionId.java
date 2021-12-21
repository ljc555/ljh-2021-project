package com.csbaic.jd.dto;

import lombok.Data;

@Data
public class GetRecUrlBySubUnionId {

    private String materialId;
    private String subUnionId;
    private String couponUrl;
    private Integer chainType;

}
