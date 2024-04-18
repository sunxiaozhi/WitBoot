package com.witboot.infrastructure.gateway.impl;

import com.witboot.domain.operationLog.gateway.OperationLogGateway;
import com.witboot.domain.operationLog.model.OperationLogEntity;
import com.witboot.infrastructure.convertor.OperationLogConvertor;
import com.witboot.infrastructure.gateway.impl.database.dataobject.OperationLogDo;
import com.witboot.infrastructure.gateway.impl.database.mapper.OperationLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * OperationLogGatewayImpl
 *
 * @author sunxiaozhi
 */
@Component("OperationLogGateway")
public class OperationLogGatewayImpl implements OperationLogGateway {
    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    @Async
    public void save(OperationLogEntity operationLogEntity) {
        OperationLogDo operationLogDo = OperationLogConvertor.toAddOperationLogDo(operationLogEntity);

        operationLogMapper.insert(operationLogDo);
    }
}
