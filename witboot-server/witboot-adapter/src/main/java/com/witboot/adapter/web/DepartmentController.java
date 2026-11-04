package com.witboot.adapter.web;

import com.witboot.client.department.api.IDepartmentService;
import com.witboot.client.department.dto.DepartmentAddCmd;
import com.witboot.client.department.dto.DepartmentUpdateCmd;
import com.witboot.client.department.dto.data.DepartmentVO;
import com.witboot.client.department.dto.query.DepartmentListByParamQuery;
import com.witboot.common.annotation.WitLog;
import com.witboot.common.core.controller.BaseController;
import com.witboot.common.response.ResponseResult;
import com.witboot.common.core.model.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 部门控制器
 *
 * @author sunxiaozhi
 */
@RestController
@RequestMapping("department/")
@Tag(name = "部门")
public class DepartmentController extends BaseController {
    @Resource
    private IDepartmentService departmentService;

    @GetMapping(value = "/list")
    @Operation(summary = "部门列表")
    @WitLog(description = "部门列表")
    public ResponseResult<PageResult<DepartmentVO>> list(DepartmentListByParamQuery departmentParamQuery) {
        startPage();
        return ResponseResult.success(departmentService.listByParam(departmentParamQuery));
    }

    @PostMapping(value = "/add")
    @Operation(summary = "部门新增")
    @WitLog(description = "部门新增")
    public ResponseResult<DepartmentVO> add(@RequestBody DepartmentAddCmd departmentAddCmd) {
        return ResponseResult.success(departmentService.add(departmentAddCmd));
    }

    @PostMapping(value = "/update")
    @Operation(summary = "部门修改")
    @WitLog(description = "部门修改")
    public ResponseResult<DepartmentVO> update(@RequestBody DepartmentUpdateCmd departmentUpdateCmd) {
        return ResponseResult.success(departmentService.update(departmentUpdateCmd));
    }

    @DeleteMapping(value = "/delete")
    @Operation(summary = "部门删除")
    @WitLog(description = "部门删除")
    public ResponseResult<Integer> delete(String[] ids) {
        return ResponseResult.success(departmentService.deleteByIds(ids));
    }
}
