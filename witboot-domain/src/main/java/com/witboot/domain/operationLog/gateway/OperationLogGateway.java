package com.witboot.domain.operationLog.gateway;

import com.witboot.domain.operationLog.model.OperationLogEntity;

/**
 * OperationLog Gateway
 *
 * @author sunxiaoizhi
 */
public interface OperationLogGateway {
    /**
     * 保存操记录
     *
     * @param operationLogEntity operationLog Domain
     * @return 操作记录实体
     */
    int save(OperationLogEntity operationLogEntity);
}
