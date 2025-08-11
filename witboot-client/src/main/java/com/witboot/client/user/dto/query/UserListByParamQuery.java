package com.witboot.client.user.dto.query;

import com.witboot.domain.base.model.query.BaseQuery;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询用户列表
 *
 * @author sunxiaoizhi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class UserListByParamQuery extends BaseQuery {
    private String keyword;

    private String name;

    private String username;
}
