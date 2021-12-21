package com.csbaic.jd.dto;

import lombok.Data;

import java.util.List;

@Data
public class ActivityDetail {

    private Activity activity;

    private List<Goods> goods;
}
