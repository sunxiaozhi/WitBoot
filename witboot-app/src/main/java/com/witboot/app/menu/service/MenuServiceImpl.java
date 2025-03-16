package com.witboot.app.menu.service;

import com.witboot.app.menu.executor.command.MenuAddCmdExe;
import com.witboot.app.menu.executor.command.MenuDeleteCmdExe;
import com.witboot.app.menu.executor.command.MenuUpdateCmdExe;
import com.witboot.app.menu.executor.query.MenuInfoQueryExe;
import com.witboot.app.menu.executor.query.MenuListByParamQueryExe;
import com.witboot.app.menu.executor.query.MenuLoginQueryExe;
import com.witboot.client.menu.api.IMenuService;
import com.witboot.client.menu.dto.MenuAddCmd;
import com.witboot.client.menu.dto.MenuUpdateCmd;
import com.witboot.client.menu.dto.data.MenuVO;
import com.witboot.client.menu.dto.query.MenuListByParamQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户相关
 *
 * @author sunxiaoizhi
 */
@Service
public class MenuServiceImpl implements IMenuService {
    /**
     * xxxExe 避免 Service 膨胀利器
     */
    @Autowired
    private MenuAddCmdExe menuAddCmdExe;

    @Autowired
    private MenuUpdateCmdExe menuUpdateCmdExe;

    @Autowired
    private MenuLoginQueryExe menuLoginQueryExe;

    @Autowired
    private MenuInfoQueryExe menuInfoQueryExe;

    @Autowired
    private MenuListByParamQueryExe menuListByParamQueryExe;

    @Autowired
    private MenuDeleteCmdExe menuDeleteCmdExe;

    @Override
    public MenuVO add (MenuAddCmd menuAddCmd) {
        return menuAddCmdExe.execute(menuAddCmd);
    }

    @Override
    public MenuVO update(MenuUpdateCmd cmd) {
        return menuUpdateCmdExe.execute(cmd);
    }

    @Override
    public MenuVO getMenuInfo(Long id) {
        return menuInfoQueryExe.execute(id);
    }

    @Override
    public List<MenuVO> listByParam(MenuListByParamQuery menuListByParamQuery) {
        return menuListByParamQueryExe.execute(menuListByParamQuery);
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
