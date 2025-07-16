package com.witboot.domain.user.query;

import lombok.Data;

/**
 * 查询用户列表
 *
 * @author sunxiaoizhi
 */
@Data
public class UserListByParamQuerySpec {
    private String name;

    private String username;

    private int pageNo;

    private int pageSize;
}
