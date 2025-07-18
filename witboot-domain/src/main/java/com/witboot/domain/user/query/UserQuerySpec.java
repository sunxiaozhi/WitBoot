package com.witboot.domain.user.query;

import com.witboot.domain.base.model.query.BaseQuerySpec;
import lombok.Getter;
import lombok.Setter;

/**
 * 查询用户
 *
 * @author sunxiaoizhi
 */
@Getter
@Setter
public class UserQuerySpec extends BaseQuerySpec {
    /**
     * 姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String username;
}
