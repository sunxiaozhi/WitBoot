package com.witboot.domain.operationlog.query;

import lombok.Data;

/**
 * 查询操作日志列表
 *
 * @author sunxiaoizhi
 */
@Data
public class OperationLogListByParamQuerySpec {
    private String ip;
    private int pageNo = 1;
    private int pageSize = 10;
}
