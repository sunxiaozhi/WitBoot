package com.witboot.app.menu.assembler;

import com.witboot.client.menu.dto.MenuAddCmd;
import com.witboot.client.menu.dto.MenuUpdateCmd;
import com.witboot.client.menu.dto.data.MenuVO;
import com.witboot.domain.menu.model.MenuEntity;

/**
 * Menu Application层转换器
 * 用于DTO与实体之间的互转
 *
 * @author sunxiaozhi
 */
public class MenuAssembler {
    public static MenuEntity toEntity(MenuAddCmd menuAddCmd) {
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setName(menuAddCmd.getName());
        menuEntity.setParentId(menuAddCmd.getParentId());

        return menuEntity;
    }

    public static MenuEntity toEntity(MenuUpdateCmd menuUpdateCmd) {
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setId(menuUpdateCmd.getId());
        menuEntity.setName(menuUpdateCmd.getName());
        /*menuEntity.setMenuname(menuUpdateCmd.getMenuname());
        menuEntity.setName(menuUpdateCmd.getName());
        menuEntity.setMobile(menuUpdateCmd.getPhoneNo());
        menuEntity.setGender(menuUpdateCmd.getGender());
        menuEntity.setBirthday(menuUpdateCmd.getBirthday());
        menuEntity.setDescription(menuUpdateCmd.getDescription());*/

        return menuEntity;
    }

    public static MenuVO toValueObject(MenuEntity menuEntity) {
        MenuVO menuVO = new MenuVO();
        menuVO.setId(menuEntity.getId());
        menuVO.setName(menuEntity.getName());
        /*menuVO.setMenuname(menuEntity.getMenuname());
        menuVO.setMobile(menuEntity.getMobile());
        menuVO.setGender(menuEntity.getGender());
        menuVO.setBirthday(menuEntity.getBirthday());
        menuVO.setDescription(menuEntity.getDescription());*/

        return menuVO;
    }
}
