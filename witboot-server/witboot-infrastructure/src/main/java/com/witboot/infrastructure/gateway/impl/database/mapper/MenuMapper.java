package com.witboot.infrastructure.gateway.impl.database.mapper;

import com.witboot.domain.menu.query.MenuQuerySpec;
import com.witboot.infrastructure.gateway.impl.database.dataobject.MenuDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Menu Mapper
 *
 * @author sunxiaoizhi
 */
@Mapper
public interface MenuMapper {
    /**
     * 新增
     *
     * @param menuDO 菜单实体
     * @return 结果
     */
    int insert(MenuDO menuDO);

    /**
     * 更新
     *
     * @param menuDO 菜单实体
     * @return 结果
     */
    int update(MenuDO menuDO);

    /**
     * 通过id查询
     *
     * @param id id
     * @return 结果
     */
    Optional<MenuDO> selectById(Long id);

    /**
     * 可带参数数据查询
     *
     * @param menuListByParamQuerySpec 查询条件
     * @return 结果
     */
    List<MenuDO> selectByParam(MenuQuerySpec menuListByParamQuerySpec);

    /**
     * 判断菜单名是否存在
     *
     * @param menuId 菜单id
     * @param menuName 菜单名称
     * @return 是否存在
     */
    Boolean existByMenuName(@Param("menuId") Long menuId, @Param("menuId") Long parentId, @Param("menuName") String menuName);

    /**
     * 删除数据
     *
     * @param ids ids
     * @return 结果
     */
    int deleteByIds(String[] ids);
}
