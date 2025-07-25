package com.witboot.client.menu.api;

import com.witboot.client.menu.dto.MenuAddCmd;
import com.witboot.client.menu.dto.MenuUpdateCmd;
import com.witboot.client.menu.dto.data.MenuVO;
import com.witboot.client.menu.dto.query.MenuListByParamQuery;
import com.witboot.domain.base.model.PageResult;

/**
 * 菜单相关
 *
 * @author sunxiaoizhi
 */
public interface IMenuService {
    /**
     * 菜单列表
     *
     * @param menuListByParamQuery 菜单查询请求
     * @return MultiResponse
     */
    PageResult<MenuVO> listByParam(MenuListByParamQuery menuListByParamQuery);

    /**
     * 新增菜单
     *
     * @param menuAddCmd 菜单新增请求
     * @return Response
     */
    MenuVO add(MenuAddCmd menuAddCmd);

    /**
     * 菜单信息修改
     *
     * @param menuUpdateCmd 菜单信息修改请求
     * @return Response
     */
    MenuVO update(MenuUpdateCmd menuUpdateCmd);

    /**
     * 获取菜单信息
     *
     * @param id 菜单ID
     * @return 菜单信息
     */
    MenuVO getMenuInfo(Long id);

    /**
     * 菜单删除
     *
     * @param ids ids
     * @return 删除数量
     */
    int deleteByIds(String[] ids);
}
