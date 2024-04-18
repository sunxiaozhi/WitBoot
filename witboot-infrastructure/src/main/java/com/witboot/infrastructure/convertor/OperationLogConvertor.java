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
        operationLogEntity.setMethod(operationLogDo.getMethod());
        operationLogEntity.setStatus(operationLogDo.getStatus());
        operationLogEntity.setRequestContent(operationLogDo.getRequestContent());
        operationLogEntity.setResponseContent(operationLogDo.getResponseContent());

        return operationLogEntity;
    }

    public static OperationLogDo toAddOperationLogDo(OperationLogEntity operationLogEntity) {
        OperationLogDo operationLogDo = new OperationLogDo();
        operationLogDo.setIp(operationLogEntity.getIp());
        operationLogDo.setLocation(operationLogEntity.getLocation());
        operationLogDo.setMethod(operationLogEntity.getMethod());
        operationLogDo.setStatus(operationLogEntity.getStatus());
        operationLogDo.setRequestContent(operationLogEntity.getRequestContent());
        operationLogDo.setResponseContent(operationLogEntity.getResponseContent());

        return operationLogDo;
    }
}
