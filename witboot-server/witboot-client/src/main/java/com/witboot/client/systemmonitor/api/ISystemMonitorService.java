package com.witboot.client.systemmonitor.api;

import java.util.Map;

/**
 * 运维监控
 *
 * @author sunxiaoizhi
 */
public interface ISystemMonitorService {
    /**
     * 监控信息
     *
     * @return 监控信息
     */
    Map<String, Object> monitorInfo(boolean fromCache);
}
