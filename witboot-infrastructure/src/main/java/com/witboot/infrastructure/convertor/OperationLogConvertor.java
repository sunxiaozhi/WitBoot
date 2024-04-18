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

        return operationLogEntity;
    }

    public static OperationLogDo toAddOperationLogDo(OperationLogEntity operationLogEntity) {
        OperationLogDo operationLogDo = new OperationLogDo();
        operationLogDo.setId(operationLogEntity.getId());

        return operationLogDo;
    }
}
