package com.witboot.domain.menu.gateway;

import com.witboot.domain.base.model.PageResult;
import com.witboot.domain.menu.model.MenuEntity;
import com.witboot.domain.menu.query.MenuQuerySpec;

/**
 * Menu Gateway
 *
 * @author sunxiaoizhi
 */
public interface MenuGateway {
    /**
     * 根据条件查询
     *
     * @param menuListByParamQuerySpec 菜单名等
     * @return List 菜单实体
     */
    PageResult<MenuEntity> findByParam(MenuQuerySpec menuListByParamQuerySpec);

    /**
     * 获取菜单信息
     *
     * @param id 菜单ID
     * @return 菜单实体
     */
    MenuEntity findById(Long id);

    /**
     * 判断菜单名是否已存在
     *
     * @param menuId   菜单ID
     * @param parentId   菜单父级ID
     * @param menuName 菜单名
     * @return true-已存在
     */
    Boolean checkByMenuName(Long menuId, Long parentId, String menuName);

    /**
     * 保存菜单
     *
     * @param menuEntity Menu Domain
     * @return 菜单实体
     */
    MenuEntity save(MenuEntity menuEntity);

    /**
     * 删除菜单
     *
     * @param ids ids
     * @return 删除
     */
    int deleteByIds(String[] ids);
}
