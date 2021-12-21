package com.csbaic.jd.extend.authentication.jwt;

import com.alibaba.fastjson.JSON;
import com.csbaic.auth.security.authentication.ResponseResultAuthenticationEndpoint;
import com.csbaic.common.result.Result;
import com.csbaic.jd.web.ResultCodes;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTResponseResultAuthenticationEndpoint extends ResponseResultAuthenticationEndpoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        if(authException instanceof UsernameNotFoundException){
            response.setStatus(HttpStatus.UNAUTHORIZED.value() );
            String body = JSON.toJSONString(Result.error(ResultCodes.USER_NOT_FOUND.getCode(), ResultCodes.USER_NOT_FOUND.getMessage()));
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(body);

        }else{
            super.commence(request, response, authException);
        }
    }

}
