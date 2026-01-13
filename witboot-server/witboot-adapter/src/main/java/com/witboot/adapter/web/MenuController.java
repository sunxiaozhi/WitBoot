package com.witboot.adapter.web;

import com.witboot.client.menu.api.IMenuService;
import com.witboot.client.menu.dto.MenuAddCmd;
import com.witboot.client.menu.dto.MenuUpdateCmd;
import com.witboot.client.menu.dto.data.MenuVO;
import com.witboot.client.menu.dto.query.MenuListByParamQuery;
import com.witboot.common.annotation.WitLog;
import com.witboot.common.core.controller.BaseController;
import com.witboot.common.response.ResponseResult;
import com.witboot.common.core.model.PageResult;
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
@Tag(name = "菜单")
public class MenuController extends BaseController {
    @Autowired
    private IMenuService menuService;

    @GetMapping(value = "/list")
    @Operation(summary = "菜单列表")
    @WitLog(description = "菜单列表")
    public ResponseResult<PageResult<MenuVO>> list(MenuListByParamQuery menuListByParamQuery) {
        startPage();
        return ResponseResult.success(menuService.listByParam(menuListByParamQuery));
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

    @GetMapping(value = "/{id}")
    @Operation(summary = "菜单详情")
    @WitLog(description = "菜单详情")
    public ResponseResult<MenuVO> info(@PathVariable Long id) {
        return ResponseResult.success(menuService.getMenuInfo(id));
    }

    @DeleteMapping(value = "/delete")
    @Operation(summary = "菜单删除")
    @WitLog(description = "菜单删除")
    public ResponseResult<Integer> delete(String[] ids) {
        return ResponseResult.success(menuService.deleteByIds(ids));
    }
}