package com.witboot.infrastructure.convertor;

import com.witboot.domain.menu.model.MenuEntity;
import com.witboot.infrastructure.common.Constants;
import com.witboot.infrastructure.gateway.impl.database.dataobject.MenuDO;
import org.springframework.beans.BeanUtils;

/**
 * MenuConvertor DO <---> Entity
 *
 * @author sunxiaoizhi
 */
public class MenuConvertor {
    public static MenuEntity toEntity(MenuDO menuDO) {
        MenuEntity menuEntity = new MenuEntity();
        /*menuEntity.setId(menuDO.getId());
        menuEntity.setName(menuDO.getName());
        menuEntity.setParentId(menuDO.getParentId());*/
        BeanUtils.copyProperties(menuDO, menuEntity);

        return menuEntity;
    }

    public static MenuDO toAddMenuDO(MenuEntity menuEntity) {
        MenuDO menuDO = new MenuDO();
        menuDO.setId(menuEntity.getId());
        menuDO.setName(menuEntity.getName());
        menuDO.setParentId(menuEntity.getParentId());
        menuDO.setDeleteFlag(Constants.NOT_DELETED_FLAG);
        menuDO.setCreator("");
        menuDO.setModifier("");

        return menuDO;
    }

    public static void toModifyMenuDO(MenuEntity menuEntity, MenuDO menuDO) {
        menuDO.setName(menuEntity.getName());
        menuDO.setParentId(menuEntity.getParentId());
    }
}
