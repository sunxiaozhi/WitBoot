package com.witboot.infrastructure.convertor;

import com.witboot.domain.operationlog.model.OperationLogEntity;
import com.witboot.infrastructure.gateway.impl.database.dataobject.OperationLogDO;

/**
 * OperationLogConvertor DO <---> Entity
 *
 * @author sunxiaoizhi
 */
public class OperationLogConvertor {
    public static OperationLogEntity toEntity(OperationLogDO operationLogDo) {
        OperationLogEntity operationLogEntity = new OperationLogEntity();
        operationLogEntity.setId(operationLogDo.getId());
        operationLogEntity.setIp(operationLogDo.getIp());
        operationLogEntity.setLocation(operationLogDo.getLocation());
        operationLogEntity.setUri(operationLogDo.getUri());
        operationLogEntity.setMethod(operationLogDo.getMethod());
        operationLogEntity.setWasteTime(operationLogDo.getWasteTime());
        operationLogEntity.setRequestParam(operationLogDo.getRequestParam());
        operationLogEntity.setRequestBody(operationLogDo.getRequestBody());
        operationLogEntity.setResponseResult(operationLogDo.getResponseResult());

        return operationLogEntity;
    }

    public static OperationLogDO toAddOperationLogDo(OperationLogEntity operationLogEntity) {
        OperationLogDO operationLogDo = new OperationLogDO();
        operationLogDo.setIp(operationLogEntity.getIp());
        operationLogDo.setLocation(operationLogEntity.getLocation());
        operationLogDo.setUri(operationLogEntity.getUri());
        operationLogDo.setMethod(operationLogEntity.getMethod());
        operationLogDo.setWasteTime(operationLogEntity.getWasteTime());
        operationLogDo.setRequestParam(operationLogEntity.getRequestParam());
        operationLogDo.setRequestBody(operationLogEntity.getRequestBody());
        operationLogDo.setResponseResult(operationLogEntity.getResponseResult());

        return operationLogDo;
    }
}
