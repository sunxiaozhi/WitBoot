package com.witboot.app.menu.executor.command;

import com.witboot.domain.menu.gateway.MenuGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MenuDeleteCmdExe
 *
 * @author sunxiaoizhi
 */
@Component
public class MenuDeleteCmdExe {
    @Autowired
    private MenuGateway menuGateway;

    public int execute(String[] ids) {
        return menuGateway.deleteByIds(ids);
    }
}
