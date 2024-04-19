package com.witboot.infrastructure.convertor;

import com.witboot.domain.operationLog.model.OperationLogEntity;
import com.witboot.infrastructure.gateway.impl.database.dataobject.OperationLogDo;

/**
 * OperationLogConvertor DO <---> Entity
 *
 * @author sunxiaoizhi
 */
public class OperationLogConvertor {
    public static OperationLogEntity toEntity(OperationLogDo operationLogDo) {
        OperationLogEntity operationLogEntity = new OperationLogEntity();
        operationLogEntity.setId(operationLogDo.getId());
        operationLogEntity.setIp(operationLogDo.getIp());
        operationLogEntity.setLocation(operationLogDo.getLocation());
        operationLogEntity.setUri(operationLogDo.getUri());
        operationLogEntity.setMethod(operationLogDo.getMethod());
        operationLogEntity.setStatus(operationLogDo.getStatus());
        operationLogEntity.setRequestParam(operationLogDo.getRequestParam());
        operationLogEntity.setRequestBody(operationLogDo.getRequestBody());
        operationLogEntity.setResponseResult(operationLogDo.getResponseResult());

        return operationLogEntity;
    }

    public static OperationLogDo toAddOperationLogDo(OperationLogEntity operationLogEntity) {
        OperationLogDo operationLogDo = new OperationLogDo();
        operationLogDo.setIp(operationLogEntity.getIp());
        operationLogDo.setLocation(operationLogEntity.getLocation());
        operationLogDo.setUri(operationLogEntity.getUri());
        operationLogDo.setMethod(operationLogEntity.getMethod());
        operationLogDo.setStatus(operationLogEntity.getStatus());
        operationLogDo.setRequestParam(operationLogEntity.getRequestParam());
        operationLogDo.setRequestBody(operationLogEntity.getRequestBody());
        operationLogDo.setResponseResult(operationLogEntity.getResponseResult());

        return operationLogDo;
    }
}
