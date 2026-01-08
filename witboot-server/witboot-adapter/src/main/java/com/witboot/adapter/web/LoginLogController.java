package com.witboot.adapter.web;

import com.witboot.client.base.dto.delete.DeleteRequest;
import com.witboot.client.loginlog.api.ILoginLogService;
import com.witboot.client.loginlog.dto.data.LoginLogVO;
import com.witboot.client.loginlog.dto.query.LoginLogListByParamQuery;
import com.witboot.domain.base.model.PageResult;
import com.witboot.infrastructure.common.annotation.WitLog;
import com.witboot.infrastructure.common.core.controller.BaseController;
import com.witboot.infrastructure.common.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 登录日志控制器
 *
 * @author sunxiaozhi
 *
 */
@RestController
@RequestMapping("loginLog/")
@Tag(name = "登录日志接口")
public class LoginLogController extends BaseController {
    @Autowired
    private ILoginLogService loginLogService;

    @GetMapping(value = "/list")
    @Operation(summary = "登录日志列表")
    @WitLog(description = "登录日志列表")
    public ResponseResult<PageResult<LoginLogVO>> list(LoginLogListByParamQuery loginLogListByParamQuery) {
        startPage();
        return ResponseResult.success(loginLogService.listByParam(loginLogListByParamQuery));
    }

    @GetMapping(value = "/info/{id}")
    @Operation(summary = "登录日志详情")
    @WitLog(description = "登录日志详情")
    public ResponseResult<LoginLogVO> loginLogInfo(@PathVariable("id") Long id) {
        return ResponseResult.success(loginLogService.getLoginLogInfo(id));
    }

    @DeleteMapping(value = "/delete")
    @Operation(summary = "登录日志删除")
    @WitLog(description = "登录日志删除")
    public ResponseResult<Integer> delete(@RequestBody DeleteRequest deleteRequest) {
        return ResponseResult.success(loginLogService.deleteByIds(deleteRequest));
    }
}