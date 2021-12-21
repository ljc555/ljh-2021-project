package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class Ids {

    @ApiModelProperty("id集合")
    private List<Long> ids;
}

