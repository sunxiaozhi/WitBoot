package com.witboot.client.operationlog.dto.query;

import com.witboot.domain.base.model.query.BaseQuery;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询操作记录
 *
 * @author sunxiaoizhi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class OperationLogListByParamQuery extends BaseQuery {
    /**
     * 查询关键词
     */
    private String searchKeyword;

    /**
     * 请求方法
     */
    private String method;
}
