package com.witboot.adapter.web;

import com.witboot.client.department.api.IDepartmentService;
import com.witboot.client.department.dto.query.DepartmentParamQuery;
import com.witboot.infrastructure.common.annotation.WitLog;
import com.witboot.infrastructure.common.core.controller.BaseController;
import com.witboot.infrastructure.common.response.ResponseListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
@Tag(name = "部门接口")
public class DepartmentController extends BaseController {
    @Autowired
    private IDepartmentService departmentService;

    @GetMapping(value = "/list")
    @Operation(summary = "列表")
    @WitLog(description = "部门列表")
    public ResponseListResult list(DepartmentParamQuery departmentParamQuery) {
        startPage();
        return ResponseListResult.listInfo(departmentService.listByParam(departmentParamQuery));
    }
}
