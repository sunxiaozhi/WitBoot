package com.witboot.infrastructure.convertor;

import com.witboot.domain.operationlog.model.OperationLogEntity;
import com.witboot.infrastructure.gateway.impl.database.dataobject.OperationLogDO;
import org.springframework.beans.BeanUtils;

/**
 * OperationLogConvertor DO <---> Entity
 *
 * @author sunxiaoizhi
 */
public class OperationLogConvertor {
    public static OperationLogEntity toEntity(OperationLogDO operationLogDo) {
        OperationLogEntity operationLogEntity = new OperationLogEntity();
        BeanUtils.copyProperties(operationLogDo, operationLogEntity);
        return operationLogEntity;
    }

    public static OperationLogDO toAddOperationLogDo(OperationLogEntity operationLogEntity) {
        OperationLogDO operationLogDo = new OperationLogDO();
        BeanUtils.copyProperties(operationLogEntity, operationLogDo);
        return operationLogDo;
    }
}
