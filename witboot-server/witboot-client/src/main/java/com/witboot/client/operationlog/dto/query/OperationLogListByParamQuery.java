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
     * ip
     */
    private String ip;
}
