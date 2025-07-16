package com.witboot.adapter.web;

import com.witboot.client.operationlog.api.IOperationLogService;
import com.witboot.client.operationlog.dto.data.OperationLogVO;
import com.witboot.client.operationlog.dto.query.OperationLogListByParamQuery;
import com.witboot.domain.base.model.PageResult;
import com.witboot.infrastructure.common.annotation.WitLog;
import com.witboot.infrastructure.common.core.controller.BaseController;
import com.witboot.infrastructure.common.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 操作日志控制器
 *
 * @author sunxiaozhi
 *
 */
@RestController
@RequestMapping("operationLog/")
@Tag(name = "操作日志接口")
public class OperationLogController extends BaseController {
    @Autowired
    private IOperationLogService operationLogService;

    @GetMapping(value = "/list")
    @Operation(summary = "操作日志列表")
    @WitLog(description = "操作日志列表")
    public ResponseResult<PageResult<OperationLogVO>> list(OperationLogListByParamQuery operationLogListByParamQuery) {
        startPage();
        return ResponseResult.success(operationLogService.listByParam(operationLogListByParamQuery));
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "操作日志详情")
    @WitLog(description = "操作日志详情")
    public ResponseResult<OperationLogVO> operationLogInfo(@PathVariable("id") Long id) {
        return ResponseResult.success(operationLogService.getOperationLogInfo(id));
    }

    @DeleteMapping(value = "/delete")
    @Operation(summary = "操作日志删除")
    @WitLog(description = "操作日志删除")
    public ResponseResult<Integer> delete(String[] ids) {
        return ResponseResult.success(operationLogService.deleteByIds(ids));
    }
}