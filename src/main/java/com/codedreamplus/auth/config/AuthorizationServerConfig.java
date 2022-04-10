package com.codedreamplus.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 认证服务配置
 */
@Order
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    AuthenticationManager authenticationManager;

    @Resource
    RedisConnectionFactory redisConnectionFactory;
    @Value("${spring.security.oauth2.selectClientDetailsSql}")
    String selectClientDetailsSql;
    @Value("${spring.security.oauth2.findClientDetailsSql}")
    String findClientDetailsSql;

    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private DataSource dataSource;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        jdbcClientDetailsService.setSelectClientDetailsSql(selectClientDetailsSql);
        jdbcClientDetailsService.setFindClientDetailsSql(findClientDetailsSql);
        clients.withClientDetails(jdbcClientDetailsService);
    }

    /**
     * 认证服务端点配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                //用户管理
                .userDetailsService(userDetailsService)
                //token存到redis
                .tokenStore(tokenStore())
                //启用oauth2管理
                .authenticationManager(authenticationManager)
                //接收POST
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.allowFormAuthenticationForClients();
    }
}
