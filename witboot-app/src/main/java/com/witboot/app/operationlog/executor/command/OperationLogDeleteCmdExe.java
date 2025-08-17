package com.witboot.app.operationlog.executor.command;

import com.witboot.domain.operationlog.gateway.OperationLogGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UserDeleteCmdExe
 *
 * @author sunxiaoizhi
 */
@Component
public class OperationLogDeleteCmdExe {
    @Autowired
    private OperationLogGateway operationLogGateway;

    public int execute(String[] ids) {
        return operationLogGateway.deleteByIds(ids);
    }
}
