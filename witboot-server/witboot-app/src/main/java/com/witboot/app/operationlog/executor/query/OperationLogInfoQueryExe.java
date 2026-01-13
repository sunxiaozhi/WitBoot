package com.witboot.app.operationlog.executor.query;

import com.witboot.app.operationlog.assembler.OperationLogAssembler;
import com.witboot.client.operationlog.dto.data.OperationLogErrorCode;
import com.witboot.client.operationlog.dto.data.OperationLogVO;
import com.witboot.domain.operationlog.gateway.OperationLogGateway;
import com.witboot.domain.operationlog.model.OperationLogEntity;
import com.witboot.common.exception.WitBootBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 用户信息查询
 *
 * @author sunxiaoizhi
 */
@Component
public class OperationLogInfoQueryExe {
    @Autowired
    private OperationLogGateway operationlogGateway;

    public OperationLogVO execute(Long id) {
        OperationLogEntity operationlogEntity = operationlogGateway.findById(id);
        if (Objects.isNull(operationlogEntity)) {
            throw new WitBootBizException(OperationLogErrorCode.B_OPERATION_LOG_UNDEFINED);
        }

        return OperationLogAssembler.toValueObject(operationlogEntity);
    }
}
