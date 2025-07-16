package com.witboot.app.menu.executor.query;

import com.witboot.app.menu.assembler.MenuAssembler;
import com.witboot.client.menu.dto.data.MenuVO;
import com.witboot.domain.base.model.PageResult;
import com.witboot.domain.menu.gateway.MenuGateway;
import com.witboot.domain.menu.model.MenuEntity;
import com.witboot.domain.menu.query.MenuListByParamQuerySpec;
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

    public PageResult<MenuVO> execute(MenuListByParamQuerySpec menuListByParamQuerySpec) {
        PageResult<MenuEntity> menuEntityPageResult = menuGateway.findByParam(menuListByParamQuerySpec);

        List<MenuVO> menuVoList = menuEntityPageResult.getList().stream()
                .map(MenuAssembler::toValueObject)
                .collect(Collectors.toList());

        return PageResult.build(menuVoList, menuEntityPageResult.getTotal());
    }
}
