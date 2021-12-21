package com.csbaic.jd.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.csbaic.auth.security.authentication.ResponseResultAuthenticationAccessDeniedHandler;
import com.csbaic.auth.security.authentication.ResponseResultAuthenticationEndpoint;
import com.csbaic.auth.security.authentication.ResponseResultAuthenticationFailureHandler;
import com.csbaic.auth.security.jwt.JwtSecurityConfigurer;
import com.csbaic.jd.extend.authentication.jwt.JWTResponseResultAuthenticationEndpoint;
import com.csbaic.jd.extend.authentication.jwt.JwtAuthenticationConverter;
import com.csbaic.jd.extend.authentication.wechat.WechatAuthenticationConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;


@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final String WECHAT_LOGIN_ENDPOINT = "/login/wechat";


    @Override
    public void init(WebSecurity web) throws Exception {
        super.init(web);


        web.ignoring(). antMatchers("/swagger-ui.html")
                .antMatchers("/webjars/**")
                .antMatchers("/v2/**")
                .antMatchers("/swagger-resources/**");


    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ApplicationContext applicationContext = getApplicationContext();
        WxMaService wxMaService = applicationContext.getBean(WxMaService.class);
        JwtAuthenticationConverter jwtAuthenticationConverter = applicationContext.getBean(JwtAuthenticationConverter.class);


        http.logout().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .csrf().disable()
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(request -> {

                    CorsConfiguration corsConfiguration  = new CorsConfiguration();
                    corsConfiguration.addAllowedOrigin(CorsConfiguration.ALL);
                    corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
                    corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))
                .exceptionHandling()
                .accessDeniedHandler(new ResponseResultAuthenticationAccessDeniedHandler())
                .authenticationEntryPoint(new ResponseResultAuthenticationEndpoint())
                .and()
                .apply(new WechatAuthenticationConfigurer())
                .wxMaService(wxMaService)
                .failureHandler(new ResponseResultAuthenticationFailureHandler())
                .requiresAuthenticationRequestMatcher(new AntPathRequestMatcher(WECHAT_LOGIN_ENDPOINT, "POST"))
                .and()
                .apply(new JwtSecurityConfigurer())
                .authenticationEntryPoint(new JWTResponseResultAuthenticationEndpoint())
                .jwtAuthenticationConverter(jwtAuthenticationConverter)

                .and()
                .authorizeRequests()

                //任何人都可以访问
                .mvcMatchers(HttpMethod.POST, WECHAT_LOGIN_ENDPOINT).permitAll()
                .mvcMatchers(HttpMethod.GET, "/login/wechat/web/authorize_url").permitAll()
                .mvcMatchers(HttpMethod.GET, "/login/wechat/web").permitAll()

                .mvcMatchers(HttpMethod.POST, "/users/wechat").permitAll()
                .mvcMatchers(HttpMethod.POST, "/users/get_by_invitation_code/{code}").permitAll()
                .mvcMatchers(HttpMethod.POST, "/users/{userId}").permitAll()
                .antMatchers("/goods/**").permitAll()
                .antMatchers("/categories/**").permitAll()
                .mvcMatchers(HttpMethod.GET, "/banners/**").permitAll()
                .mvcMatchers(HttpMethod.GET,"/timeline_goods/**").permitAll()
                .mvcMatchers(HttpMethod.GET, "/news").permitAll()
                .mvcMatchers(HttpMethod.GET, "/options/app").permitAll() //App配置获取
                .mvcMatchers(HttpMethod.GET, "/activities/{id}/goods").permitAll()
                .mvcMatchers(HttpMethod.GET, "/activities/{id}").permitAll()
                .mvcMatchers(HttpMethod.GET, "/faq/**").permitAll()
                .mvcMatchers(HttpMethod.GET, "/admin/index.html").permitAll()
                .mvcMatchers(HttpMethod.GET, "/admin").permitAll()
                .mvcMatchers(HttpMethod.GET, "/posters").permitAll()
                .mvcMatchers(HttpMethod.GET, "/messages/{id}").permitAll()
                .antMatchers("/static/**").permitAll()
                .mvcMatchers(HttpMethod.GET, "/quick_start").permitAll()



                //会员可访问
                .antMatchers("/users/**").fullyAuthenticated()
                .antMatchers("/wallet/**").fullyAuthenticated()
                .antMatchers("/qrcode/**").fullyAuthenticated()
                .antMatchers("/order/**").fullyAuthenticated()
                .antMatchers("/members/**").fullyAuthenticated()
                .antMatchers("/goods_url/**").fullyAuthenticated()
                .mvcMatchers(HttpMethod.POST, "/feedback").fullyAuthenticated()
                .antMatchers("/super_members/**").fullyAuthenticated()
                .mvcMatchers(HttpMethod.POST, "/upload/app").fullyAuthenticated()
                //管理员全部可以访问
                .anyRequest()
                .hasRole("ADMIN");

    }




}
