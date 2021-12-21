package com.csbaic.jd.extend.authentication.jwt;

import com.csbaic.jd.entity.UserEntity;
import com.csbaic.jd.enums.Role;
import com.csbaic.jd.service.IUserService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Component
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final IUserService userService;

    @Autowired
    public JwtAuthenticationConverter(IUserService userService) {
        this.userService = userService;
    }


    public AbstractAuthenticationToken convert(Jwt source) {
        Long userId = source.getClaim("user_id");
        UserEntity userEntity = userService.getById(userId);

        if(userEntity == null){
//            return null;
            throw new UsernameNotFoundException("Not found");
        }

        String role = "ROLE_" + (Strings.isNullOrEmpty(userEntity.getRoleName()) ? Role.MEMBER.name() : userEntity.getRoleName());
        JwtAuthenticationToken authenticationToken =  new JwtAuthenticationToken(userEntity, Collections.singletonList(new SimpleGrantedAuthority(role)));
        authenticationToken.setAuthenticated(true);
        return authenticationToken;
    }
}
