package com.witboot.app.user.executor.command;

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

    public int execute(String[] ids) {
        return userGateway.deleteByIds(ids);
    }
}
