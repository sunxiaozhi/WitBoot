package com.witboot.app.systemmonitor.service;


import com.witboot.app.systemmonitor.executor.command.MonitorInfoCmdExe;
import com.witboot.client.systemmonitor.api.ISystemMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 运维监控相关
 *
 * @author sunxiaoizhi
 */
@Service
public class SystemMonitorServiceImpl implements ISystemMonitorService {
    @Autowired
    private MonitorInfoCmdExe monitorInfoCmdExe;

    @Override
    public Map<String, Object> monitorInfo() {
        return monitorInfoCmdExe.execute();
    }
}
