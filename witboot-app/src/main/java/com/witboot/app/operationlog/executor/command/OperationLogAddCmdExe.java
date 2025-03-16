package com.witboot.app.operationlog.executor.command;

import com.witboot.app.operationlog.assembler.OperationLogAssembler;
import com.witboot.client.operationlog.dto.OperationLogAddCmd;
import com.witboot.client.operationlog.dto.data.OperationLogVO;
import com.witboot.domain.operationlog.gateway.OperationLogGateway;
import com.witboot.domain.operationlog.model.OperationLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * OperationLogInsertCmdExe
 *
 * @author sunxiaoizhi
 */
@Component
public class OperationLogAddCmdExe {

    @Autowired
    private OperationLogGateway operationlogGateway;

    public OperationLogVO execute(OperationLogAddCmd operationLogAddCmd) {
        // check 用户名是否重复
        /*if (operationlogGateway.checkByOperationLogName(null, operationLogAddCmd.getOperationLogName())) {
            throw new WitBootBizException(OperationLogErrorCode.B_USER_USERNAME_REPEAT);
        }*/

        OperationLogEntity operationlogEntity = operationlogGateway.save(OperationLogAssembler.toEntity(operationLogAddCmd));

        return OperationLogAssembler.toValueObject(operationlogEntity);
    }
}
