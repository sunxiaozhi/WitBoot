package com.witboot.app.menu.executor.command;

import com.witboot.app.menu.assembler.MenuAssembler;
import com.witboot.client.menu.dto.MenuAddCmd;
import com.witboot.client.menu.dto.data.MenuVO;
import com.witboot.domain.menu.gateway.MenuGateway;
import com.witboot.domain.menu.model.MenuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MenuInsertCmdExe
 *
 * @author sunxiaoizhi
 */
@Component
public class MenuAddCmdExe {

    @Autowired
    private MenuGateway menuGateway;

    public MenuVO execute(MenuAddCmd menuAddCmd) {
        MenuEntity menuEntity = menuGateway.save(MenuAssembler.toEntity(menuAddCmd));

        return MenuAssembler.toValueObject(menuEntity);
    }
}
