package com.witboot.infrastructure.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.witboot.client.menu.dto.data.MenuErrorCode;
import com.witboot.domain.base.model.PageResult;
import com.witboot.domain.menu.gateway.MenuGateway;
import com.witboot.domain.menu.model.MenuEntity;
import com.witboot.domain.menu.query.MenuQuerySpec;
import com.witboot.infrastructure.common.Constants;
import com.witboot.infrastructure.common.exception.WitBootBizException;
import com.witboot.infrastructure.convertor.MenuConvertor;
import com.witboot.infrastructure.gateway.impl.database.dataobject.MenuDO;
import com.witboot.infrastructure.gateway.impl.database.mapper.MenuMapper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * MenuGatewayImpl
 *
 * @author sunxiaoizhi
 */
@Component("menuGateway")
public class MenuGatewayImpl implements MenuGateway {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public PageResult<MenuEntity> findByParam(MenuQuerySpec menuListByParamQuerySpec) {
        PageHelper.startPage(menuListByParamQuerySpec.getPageNo(), menuListByParamQuerySpec.getPageSize());
        List<MenuDO> menuDOList = menuMapper.selectByParam(menuListByParamQuerySpec);
        PageInfo<MenuDO> pageInfo = new PageInfo<>(menuDOList);

        List<MenuEntity> menuEntityList = menuDOList.stream().map(MenuConvertor::toEntity).collect(Collectors.toList());

        return PageResult.build(menuEntityList,pageInfo.getTotal());
    }

    @Override
    public MenuEntity findById(Long id) {
        Optional<MenuDO> findById = menuMapper.selectById(id);
        if (findById.isEmpty()) {
            throw new WitBootBizException(MenuErrorCode.B_MENU_UNDEFINED);
        }

        MenuDO menuDO = findById.get();

        return MenuConvertor.toEntity(menuDO);
    }

    @Override
    public Boolean checkByMenuName(Long menuId, Long parentId, String menuName) {
        return menuMapper.existByMenuName(menuId, parentId, menuName);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public MenuEntity save(MenuEntity menuEntity) {
        // 新增
        if (Objects.isNull(menuEntity.getId())) {
            return addMenu(menuEntity);
        }

        // 修改
        return updateMenu(menuEntity);
    }

    /**
     * 新增菜单
     */
    private MenuEntity addMenu(MenuEntity menuEntity) {
        // 初始化菜单信息
        MenuDO menuDO = MenuConvertor.toAddMenuDO(menuEntity);

        int insert = menuMapper.insert(menuDO);
        if (insert < Constants.ONE) {
            throw new PersistenceException("保存菜单异常");
        }

        return MenuConvertor.toEntity(menuDO);
    }

    /**
     * 修改菜单
     */
    private MenuEntity updateMenu(MenuEntity menuEntity) {
        Optional<MenuDO> findById = menuMapper.selectById(menuEntity.getId());
        if (findById.isEmpty()) {
            throw new WitBootBizException(MenuErrorCode.B_MENU_UNDEFINED);
        }

        MenuDO menuDO = findById.get();

        // 更新菜单信息
        MenuConvertor.toModifyMenuDO(menuEntity, menuDO);

        // 保存menuDO
        int update = menuMapper.update(menuDO);
        if (update < Constants.ONE) {
            throw new PersistenceException("更新菜单异常");
        }

        return MenuConvertor.toEntity(menuDO);
    }

    /**
     * 删除菜单
     *
     * @param ids ids
     * @return 删除
     */
    @Override
    public int deleteByIds(String[] ids){
        return menuMapper.deleteByIds(ids);
    }
}
