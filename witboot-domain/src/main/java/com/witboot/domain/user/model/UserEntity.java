package com.witboot.domain.user.model;

import lombok.Data;

import java.time.LocalDate;

/**
 * User Domain
 *
 * @author sunxiaoizhi
 */
@Data
public class UserEntity {

    private Long id;

    /**
     * 用户名
     */
    private UserName username;
    /**
     * 密码
     */
    private UserPassword password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 描述
     */
    private String description;
}
