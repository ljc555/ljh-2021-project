package com.csbaic.jd.dto;

import lombok.Data;

@Data
public class OAuthAuthorizeUrl {

    private String url;

    public OAuthAuthorizeUrl(String url) {
        this.url = url;
    }
}
