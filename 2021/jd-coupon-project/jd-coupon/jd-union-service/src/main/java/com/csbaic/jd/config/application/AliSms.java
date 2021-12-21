package com.csbaic.jd.config.application;

import lombok.Data;

@Data
public class AliSms {

    private String accessKeyId;
    private String accessSecret;
    private String signName;
    private String templateCode;


}
