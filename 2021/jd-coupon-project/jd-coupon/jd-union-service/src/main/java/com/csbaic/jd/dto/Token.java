package com.csbaic.jd.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Token {

    /***
     * 第三方平台唯一id
     */
    private String token;
}
