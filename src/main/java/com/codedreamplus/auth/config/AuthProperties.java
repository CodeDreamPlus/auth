package com.codedreamplus.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 鉴权配置类
 */
@Data
@ConfigurationProperties(prefix = "spring.security.resource-server")
public class AuthProperties {
	/**
	 * 过滤鉴权的url
	 */
	private List<String> skipUrl = new ArrayList<>();
}
