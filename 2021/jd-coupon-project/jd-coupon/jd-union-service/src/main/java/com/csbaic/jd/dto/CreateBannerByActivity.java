package com.csbaic.jd.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CreateBannerByActivity {

    @ApiModelProperty("活动id")
    private List<Long> activities;
}

