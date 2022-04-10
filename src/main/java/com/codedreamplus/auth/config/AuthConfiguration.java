package com.codedreamplus.auth.config;

import com.codedreamplus.auto.properties.CodeDreamPropertySource;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Auth自动配置
 * <p>需要加载</p>
 */
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(AuthProperties.class)
@CodeDreamPropertySource(value = "classpath:/codedreamplus-auth.yml")
public class AuthConfiguration {

}

