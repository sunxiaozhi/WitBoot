package com.witboot.adapter.web;

import com.witboot.client.systemmonitor.api.ISystemMonitorService;
import com.witboot.common.annotation.WitLog;
import com.witboot.common.core.controller.BaseController;
import com.witboot.common.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 运维监控控制器
 *
 * @author sunxiaozhi
 */
@RestController
@RequestMapping("systemMonitor/")
@Tag(name = "运维监控")
public class SystemMonitorController extends BaseController {

    /**
     * 系统监控服务接口
     */
    @Autowired
    private ISystemMonitorService systemMonitorService;

    /**
     * 获取系统监控信息
     *
     * @return 返回系统监控信息的响应结果
     */
    @GetMapping(value = "/monitorInfo")
    @Operation(summary = "获取系统监控信息")
    @WitLog(description = "运维监控信息")
    public ResponseResult<Map<String, Object>> monitorInfo(@Parameter(description = "是否从缓存中获取") boolean fromCache) {
        return ResponseResult.success(systemMonitorService.monitorInfo(fromCache));
    }
}
