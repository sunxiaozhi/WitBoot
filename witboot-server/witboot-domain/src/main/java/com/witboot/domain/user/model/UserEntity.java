package com.witboot.domain.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * User Domain
 *
 * @author sunxiaoizhi
 */
@Data
public class UserEntity {
    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

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
    private String gender;

    /**
     * 生日
     */
    @DateTimeFormat
    @JsonFormat
    private LocalDate birthday;

    /**
     * 描述
     */
    private String description;
}
