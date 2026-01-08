package com.witboot.app.loginlog.executor.command;

import com.witboot.domain.base.model.delete.DeleteRequestSpec;
import com.witboot.domain.loginlog.gateway.LoginLogGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UserDeleteCmdExe
 *
 * @author sunxiaoizhi
 */
@Component
public class LoginLogDeleteCmdExe {
    @Autowired
    private LoginLogGateway loginLogGateway;

    public int execute(DeleteRequestSpec deleteRequestSpec) {
        return loginLogGateway.deleteByIds(deleteRequestSpec);
    }
}
