package com.witboot.app.systemmonitor.executor.command;

import com.witboot.domain.systemmonitor.gateway.SystemMonitorGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MonitorInfoCmdExe {
    @Autowired
    private SystemMonitorGateway systemMonitorGateway;

    public Map<String, Object> execute(boolean fromCache) {
        return systemMonitorGateway.monitorInfo(fromCache);
    }
}
