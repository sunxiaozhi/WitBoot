package com.witboot.adapter.task;

import com.witboot.client.systemmonitor.api.ISystemMonitorService;
import com.witboot.common.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SystemMonitorTask {
    /**
     * 系统监控服务接口
     */
    @Autowired
    private ISystemMonitorService systemMonitorService;

    @Scheduled(cron = "${cron.monitor-info}")
    public void monitorInfo() {
        log.info("监控任务执行...");
        ResponseResult.success(systemMonitorService.monitorInfo(false));
    }
}
