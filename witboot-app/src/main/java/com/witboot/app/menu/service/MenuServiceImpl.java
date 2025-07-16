package com.witboot.app.menu.service;

import com.witboot.app.menu.assembler.MenuAssembler;
import com.witboot.app.menu.executor.command.MenuAddCmdExe;
import com.witboot.app.menu.executor.command.MenuDeleteCmdExe;
import com.witboot.app.menu.executor.command.MenuUpdateCmdExe;
import com.witboot.app.menu.executor.query.MenuInfoQueryExe;
import com.witboot.app.menu.executor.query.MenuListByParamQueryExe;
import com.witboot.client.menu.api.IMenuService;
import com.witboot.client.menu.dto.MenuAddCmd;
import com.witboot.client.menu.dto.MenuUpdateCmd;
import com.witboot.client.menu.dto.data.MenuVO;
import com.witboot.client.menu.dto.query.MenuListByParamQuery;
import com.witboot.domain.base.model.PageResult;
import com.witboot.domain.menu.query.MenuListByParamQuerySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户相关
 *
 * @author sunxiaoizhi
 */
@Service
public class MenuServiceImpl implements IMenuService {
    /**
     * Exe 避免 Service 膨胀利器
     */
    @Autowired
    private MenuAddCmdExe menuAddCmdExe;

    @Autowired
    private MenuUpdateCmdExe menuUpdateCmdExe;

    @Autowired
    private MenuInfoQueryExe menuInfoQueryExe;

    @Autowired
    private MenuListByParamQueryExe menuListByParamQueryExe;

    @Autowired
    private MenuDeleteCmdExe menuDeleteCmdExe;

    @Override
    public PageResult<MenuVO> listByParam(MenuListByParamQuery menuListByParamQuery) {
        MenuListByParamQuerySpec menuListByParamQuerySpec = MenuAssembler.toMenuListByParamQuerySpec(menuListByParamQuery);
        return menuListByParamQueryExe.execute(menuListByParamQuerySpec);
    }

    @Override
    public MenuVO add (MenuAddCmd menuAddCmd) {
        return menuAddCmdExe.execute(menuAddCmd);
    }

    @Override
    public MenuVO update(MenuUpdateCmd menuUpdateCmd) {
        return menuUpdateCmdExe.execute(menuUpdateCmd);
    }

    @Override
    public MenuVO getMenuInfo(Long id) {
        return menuInfoQueryExe.execute(id);
    }

    /**
     * 用户删除
     * @param ids ids
     * @return 删除数量
     */
    @Override
    public int deleteByIds(String[] ids){
        return menuDeleteCmdExe.execute(ids);
    }
}
