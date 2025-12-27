package com.witboot.app.menu.executor.command;

import com.witboot.app.menu.assembler.MenuAssembler;
import com.witboot.client.menu.dto.MenuUpdateCmd;
import com.witboot.client.menu.dto.data.MenuVO;
import com.witboot.domain.menu.gateway.MenuGateway;
import com.witboot.domain.menu.model.MenuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * menuUpdateCmdExe
 *
 * @author sunxiaoizhi
 */
@Component
public class MenuUpdateCmdExe {
    @Autowired
    private MenuGateway menuGateway;

    public MenuVO execute(MenuUpdateCmd menuUpdateCmd) {
        // check 用户名是否重复
        /*if (menuGateway.checkByMenuname(menuUpdateCmd.getId(), menuUpdateCmd.getMenuname())) {
            throw new WitBootBizException(MenuErrorCode.B_USER_USERNAME_REPEAT);
        }*/

        MenuEntity menuEntity = menuGateway.save(MenuAssembler.toEntity(menuUpdateCmd));
        return MenuAssembler.toValueObject(menuEntity);
    }
}
