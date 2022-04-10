package com.codedreamplus.auth.entity;

import lombok.Data;

import java.util.Date;

/**
 * <p>Description: [用户]</p>
 * Created on 2020年7月13日
 *
 * @author mo
 */
@Data
public class UserEntity {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private Long updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 状态[1:正常]
     */
    private Integer status;

    /**
     * 状态[0:未删除,1:删除]
     */
    private Integer isDeleted;
    /**
     * 用户名
     */
    private String name;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户头像
     */
    private String headIcon;

    /**
     * 手机号
     */
    private String phone;
}