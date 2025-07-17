package com.witboot.adapter.web;

import com.witboot.client.department.api.IDepartmentService;
import com.witboot.client.department.dto.data.DepartmentVO;
import com.witboot.client.department.dto.query.DepartmentListByParamQuery;
import com.witboot.domain.base.model.PageResult;
import com.witboot.infrastructure.common.annotation.WitLog;
import com.witboot.infrastructure.common.core.controller.BaseController;
import com.witboot.infrastructure.common.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @DeleteMapping(value = "/delete")
    @Operation(summary = "部门删除")
    @WitLog(description = "部门删除")
    public ResponseResult<Integer> delete(String[] ids) {
        return ResponseResult.success(departmentService.deleteByIds(ids));
    }
}
