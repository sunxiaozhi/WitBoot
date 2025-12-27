package com.witboot.app.operationlog.executor.command;

import com.witboot.domain.base.model.delete.DeleteRequestSpec;
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

    public int execute(DeleteRequestSpec deleteRequestSpec) {
        return operationLogGateway.deleteByIds(deleteRequestSpec);
    }
}
