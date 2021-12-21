package com.csbaic.jd;

import com.csbaic.auth.accesstoken.AccessTokenService;
import com.csbaic.auth.security.jwt.JWTAccessTokenService;

import java.util.HashMap;
import java.util.Map;

public class TokenBuilder {

    public static void main(String[] args){
        AccessTokenService accessTokenService = new JWTAccessTokenService("jwt:key:jd-union-xxxxxxxxxxxxxxxx", "HS256");
        Map<String, Object> obj = new HashMap<>();
        obj.put("user_id", 1243810041125154818L);
        String token = accessTokenService.encode(obj);
        System.out.print(token);

    }
}
