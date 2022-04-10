package com.codedreamplus.auth.config;


import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>Description: [登出清空accessToken]</p >
 * Created on 2020/12/1
 *
 * @author mo
 */
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    private static String BEARER_AUTHENTICATION = "Bearer";

    private static String HEADER_AUTHENTICATION = "authorization";

    @Resource
    private TokenStore tokenStore;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String auth = httpServletRequest.getHeader(HEADER_AUTHENTICATION);
        if (auth != null && auth.startsWith(BEARER_AUTHENTICATION)) {
            auth = auth.split(" ")[1];
        }

        if (auth != null) {
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(auth);
            if (accessToken != null) {
                tokenStore.removeAccessToken(accessToken);
            }
        }
    }

}