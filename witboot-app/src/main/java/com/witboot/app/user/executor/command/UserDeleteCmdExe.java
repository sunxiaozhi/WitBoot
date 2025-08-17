package com.witboot.app.user.executor.command;

import com.witboot.domain.base.model.delete.DeleteRequestSpec;
import com.witboot.domain.user.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UserDeleteCmdExe
 *
 * @author sunxiaoizhi
 */
@Component
public class UserDeleteCmdExe {
    @Autowired
    private UserGateway userGateway;

    public int execute(DeleteRequestSpec deleteRequestSpec) {
        return userGateway.deleteByIds(deleteRequestSpec);
    }
}
