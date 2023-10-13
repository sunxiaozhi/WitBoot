package com.witboot.client.user.dto.query;

import com.alibaba.cola.dto.Query;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 查询用户列表
 *
 * @author sunxiaoizhi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class UserListByParamQuery extends Query {

    @Serial
    private static final long serialVersionUID = -2794036665910524477L;

    private String name;

    private String username;
}
