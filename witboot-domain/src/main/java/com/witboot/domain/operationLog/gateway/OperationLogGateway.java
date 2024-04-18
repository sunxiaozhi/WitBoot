package com.witboot.domain.operationLog.gateway;

import com.witboot.domain.operationLog.model.OperationLogEntity;
import org.springframework.scheduling.annotation.Async;

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
     */
    @Async
    void save(OperationLogEntity operationLogEntity);
}
