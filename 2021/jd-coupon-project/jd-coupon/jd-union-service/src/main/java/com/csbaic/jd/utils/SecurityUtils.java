package com.csbaic.jd.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Spring Security 工具类
 */
public final class SecurityUtils {

    /**
     * 获取Principal
     * @return
     */
    public static Object getPrincipal(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getPrincipal() : null;
    }

    /**
     * 获取JWT数据
     * @return
     */
    public static Object getClaim(String key){
        Object principal = getPrincipal();

        if(!(principal instanceof Jwt)){
            return null;
        }

        Jwt jwt = (Jwt) principal;
        return jwt.getClaim(key);
    }



}
