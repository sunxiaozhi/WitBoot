package com.witboot.app.operationlog.executor.command;

import com.witboot.domain.user.gateway.UserGateway;
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
    private UserGateway userGateway;

    public int execute(String[] ids) {
        return userGateway.deleteByIds(ids);
    }
}
