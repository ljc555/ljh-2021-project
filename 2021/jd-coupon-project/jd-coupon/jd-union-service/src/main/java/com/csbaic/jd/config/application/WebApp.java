package com.csbaic.jd.config.application;

import lombok.Data;

@Data
public class WebApp {


    private String appid = "wx19a253443fee0c0c";

    private String secret = "8f273b3f6de89d69614cecfbef557ad6";

    private String redirectUri = "https://jd-api.csbaic.com/login/wechat/web";

}
