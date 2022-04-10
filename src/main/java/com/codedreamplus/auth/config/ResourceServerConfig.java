package com.codedreamplus.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Resource
    private LogoutSuccessHandler logoutSuccessHandler;
    @Resource
    private AuthProperties authProperties;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        List<String> skipUrl = authProperties.getSkipUrl();
        skipUrl.addAll(Arrays.asList( "/oauth/**",
                "/v2/api-docs",
                "/swagger-resources/configuration/ui",
                "/swagger-resources",
                "/swagger-resources/configuration/security",
                "/swagger-ui.html",
                "/css/**",
                "/js/**",
                "/images/**",
                "/webjars/**",
                "**/favicon.ico",
                "/index"));
        httpSecurity
                .logout()
                .logoutUrl("/user/logout")//登出地址
                .logoutSuccessHandler(logoutSuccessHandler)//登出做的操作
                .and()
                .authorizeRequests()
                .antMatchers(StringUtils.toStringArray(skipUrl))
                .permitAll()
                .antMatchers("/**")
                .authenticated();      //必须认证过后才可以访问
    }
}
