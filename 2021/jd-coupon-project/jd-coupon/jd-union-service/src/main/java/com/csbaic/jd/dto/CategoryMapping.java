package com.csbaic.jd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryMapping {


    private Long id;


    /**
     * 京东分类ID;
     */
    private Long jid;


    /**
     * 分类名称
     */
    private String name;



}
