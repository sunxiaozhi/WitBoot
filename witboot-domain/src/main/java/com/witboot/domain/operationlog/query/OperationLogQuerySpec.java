package com.witboot.domain.operationlog.query;

import com.witboot.domain.base.model.query.BaseQuerySpec;
import lombok.Getter;
import lombok.Setter;

/**
 * 查询操作日志
 *
 * @author sunxiaoizhi
 */
@Getter
@Setter
public class OperationLogQuerySpec extends BaseQuerySpec {
    /**
     * ip
     */
    private String ip;
}
