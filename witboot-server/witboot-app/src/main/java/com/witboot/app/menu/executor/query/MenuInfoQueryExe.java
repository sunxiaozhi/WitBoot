package com.witboot.app.menu.executor.query;

import com.witboot.app.menu.assembler.MenuAssembler;
import com.witboot.client.menu.dto.data.MenuErrorCode;
import com.witboot.client.menu.dto.data.MenuVO;
import com.witboot.domain.menu.gateway.MenuGateway;
import com.witboot.domain.menu.model.MenuEntity;
import com.witboot.common.exception.WitBootBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 菜单信息查询
 *
 * @author sunxiaoizhi
 */
@Component
public class MenuInfoQueryExe {
    @Autowired
    private MenuGateway menuGateway;

    public MenuVO execute(Long id) {
        MenuEntity menuEntity = menuGateway.findById(id);
        if (Objects.isNull(menuEntity)) {
            throw new WitBootBizException(MenuErrorCode.B_MENU_NAME_REPEAT);
        }

        return MenuAssembler.toValueObject(menuEntity);
    }
}
