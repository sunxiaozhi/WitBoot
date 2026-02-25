package com.witboot.app.menu.executor.query;

import com.witboot.app.menu.assembler.MenuAssembler;
import com.witboot.client.menu.dto.data.MenuVO;
import com.witboot.common.core.model.PageResult;
import com.witboot.common.response.ResponseResult;
import com.witboot.domain.menu.gateway.MenuGateway;
import com.witboot.domain.menu.model.MenuEntity;
import com.witboot.domain.menu.query.MenuQuerySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * MenuListByParamQueryExe
 *
 * @author sunxiaoizhi
 */
@Component
public class MenuListByParamQueryExe {

    @Autowired
    private MenuGateway menuGateway;

    public List<MenuVO> execute(MenuQuerySpec menuListByParamQuerySpec) {
        List<MenuEntity> menuEntityList = menuGateway.findByParam(menuListByParamQuerySpec);

        return menuEntityList.stream()
                .map(MenuAssembler::toValueObject)
                .collect(Collectors.toList());
    }
}
