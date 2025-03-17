package com.witboot.adapter.web;

import com.witboot.client.menu.api.IMenuService;
import com.witboot.client.menu.dto.MenuAddCmd;
import com.witboot.client.menu.dto.MenuUpdateCmd;
import com.witboot.client.menu.dto.data.MenuVO;
import com.witboot.client.menu.dto.query.MenuListByParamQuery;
import com.witboot.infrastructure.common.annotation.WitLog;
import com.witboot.infrastructure.common.core.controller.BaseController;
import com.witboot.infrastructure.common.response.ResponseListResult;
import com.witboot.infrastructure.common.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 菜单控制器
 *
 * @author sunxiaozhi
 */
@RestController
@RequestMapping("menu/")
@Tag(name = "菜单接口")
public class MenuController extends BaseController {
    @Autowired
    private IMenuService menuService;

    @GetMapping(value = "/list")
    @Operation(summary = "菜单列表")
    @WitLog(description = "菜单列表")
    public ResponseListResult list(MenuListByParamQuery menuListByParamQuery) {
        startPage();
        return ResponseListResult.listInfo(menuService.listByParam(menuListByParamQuery));
    }

    @PostMapping(value = "/add")
    @Operation(summary = "菜单新增")
    @WitLog(description = "菜单新增")
    public ResponseResult<MenuVO> add(@RequestBody MenuAddCmd menuAddCmd) {
        return ResponseResult.success(menuService.add(menuAddCmd));
    }

    @PostMapping(value = "/update")
    @Operation(summary = "菜单修改")
    @WitLog(description = "菜单修改")
    public ResponseResult<MenuVO> update(@RequestBody MenuUpdateCmd menuUpdateCmd) {
        return ResponseResult.success(menuService.update(menuUpdateCmd));
    }

    @DeleteMapping(value = "/delete")
    @Operation(summary = "菜单删除")
    @WitLog(description = "菜单删除")
    public ResponseResult<Integer> delete(String[] ids) {
        return ResponseResult.success(menuService.deleteByIds(ids));
    }
}