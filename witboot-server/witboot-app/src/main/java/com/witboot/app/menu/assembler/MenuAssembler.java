package com.witboot.app.menu.assembler;

import com.witboot.client.menu.dto.query.MenuListByParamQuery;
import com.witboot.client.menu.dto.MenuAddCmd;
import com.witboot.client.menu.dto.MenuUpdateCmd;
import com.witboot.client.menu.dto.data.MenuVO;
import com.witboot.domain.menu.query.MenuQuerySpec;
import com.witboot.domain.menu.model.MenuEntity;
import org.springframework.beans.BeanUtils;

/**
 * Menu Application层转换器
 * 用于DTO与实体之间的互转
 *
 * @author sunxiaozhi
 */
public class MenuAssembler {
    public static MenuQuerySpec toMenuListByParamQuerySpec (MenuListByParamQuery menuListByParamQuery) {
        MenuQuerySpec menuListByParamQuerySpec = new MenuQuerySpec();
        BeanUtils.copyProperties(menuListByParamQuery, menuListByParamQuerySpec);
        return menuListByParamQuerySpec;
    }
    
    public static MenuEntity toEntity(MenuAddCmd menuAddCmd) {
        MenuEntity menuEntity = new MenuEntity();
        BeanUtils.copyProperties(menuAddCmd, menuEntity);
        return menuEntity;
    }

    public static MenuEntity toEntity(MenuUpdateCmd menuUpdateCmd) {
        MenuEntity menuEntity = new MenuEntity();
        BeanUtils.copyProperties(menuUpdateCmd, menuEntity);
        return menuEntity;
    }

    public static MenuVO toValueObject(MenuEntity menuEntity) {
        MenuVO menuVO = new MenuVO();
        BeanUtils.copyProperties(menuEntity, menuVO);
        return menuVO;
    }
}
