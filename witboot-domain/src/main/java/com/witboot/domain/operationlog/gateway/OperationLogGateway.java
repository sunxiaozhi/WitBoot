package com.witboot.domain.operationlog.gateway;

import com.witboot.client.operationlog.dto.query.OperationLogListByParamQuery;
import com.witboot.domain.operationlog.model.OperationLogEntity;

import java.util.List;

/**
 * OperationLog Gateway
 *
 * @author sunxiaoizhi
 */
public interface OperationLogGateway {
    /**
     * 根据条件查询
     *
     * @param operationLogListByParamQuery ip等
     * @return List 操作日志实体集合
     */
    List<OperationLogEntity> findByParam(OperationLogListByParamQuery operationLogListByParamQuery);

    /**
     * 获取操作日志信息
     *
     * @param id 操作日志ID
     * @return 操作日志实体
     */
    OperationLogEntity findById(Long id);
    
    /**
     * 保存操作记录
     *
     * @param operationLogEntity operationLog Domain
     */
    OperationLogEntity save(OperationLogEntity operationLogEntity);
}
