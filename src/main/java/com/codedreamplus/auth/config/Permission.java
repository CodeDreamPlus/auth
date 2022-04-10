package com.codedreamplus.auth.config;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * 权限
 */
@Data
public class Permission implements GrantedAuthority {
    /**
     * 权限名称
     */
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
