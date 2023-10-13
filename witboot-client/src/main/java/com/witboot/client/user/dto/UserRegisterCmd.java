package com.witboot.client.user.dto;

import com.alibaba.cola.dto.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDate;

/**
 * 新增用户请求
 *
 * @author sunxiaoizhi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserRegisterCmd extends Command {

    @Serial
    private static final long serialVersionUID = -5726685703640910355L;

    public UserRegisterCmd(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

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
