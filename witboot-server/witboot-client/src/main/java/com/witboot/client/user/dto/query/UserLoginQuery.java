package com.witboot.client.user.dto.query;

import com.alibaba.cola.dto.Query;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 用户登录请求
 *
 * @author sunxiaoizhi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserLoginQuery extends Query {
    @Serial
    private static final long serialVersionUID = 5180182193190154824L;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
