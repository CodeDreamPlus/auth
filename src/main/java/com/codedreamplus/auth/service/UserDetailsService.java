package com.codedreamplus.auth.service;


import com.codedreamplus.auth.config.AuthUser;
import com.codedreamplus.auth.config.Permission;
import com.codedreamplus.auth.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Resource
    private JdbcTemplate jdbcTemplate;
    @Value("${spring.security.userdetails.findUsersSql}")
    String findUsersSql;
    @Value("${spring.security.userdetails.findRoleAndFunctionSql}")
    String findRoleAndFunctionSql;

    /**
     * 实现UserDetailsService中的loadUserByUsername方法，用于加载用户数据
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserEntity> userList = jdbcTemplate.query(findUsersSql,  new BeanPropertyRowMapper<>(UserEntity.class), username);
        if (CollectionUtils.isEmpty(userList)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        UserEntity userEntity = userList.get(0);
        //查询当前用户关联权限
        List<Permission> permissions = jdbcTemplate.query(findRoleAndFunctionSql, new BeanPropertyRowMapper<>(Permission.class), userEntity.getId());
        return new AuthUser(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getUsername(),
                userEntity.getPassword(),
                true,
                true,
                true,
                true,
                permissions);
    }
}
