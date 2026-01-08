package com.witboot.client.loginlog.dto.query;

import com.witboot.domain.base.model.query.BaseQuery;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询登录日志
 *
 * @author sunxiaoizhi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class LoginLogListByParamQuery extends BaseQuery {
    /**
     * 查询关键词
     */
    private String searchKeyword;

    /**
     * 请求方法
     */
    private String method;
}
