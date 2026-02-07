package com.witboot.domain.systemmonitor.gateway;

import java.util.Map;

/**
 * User Gateway
 *
 * @author sunxiaoizhi
 */
public interface SystemMonitorGateway {
    /**
     * 获取监控信息
     * @return 监控信息
     */
    Map<String, Object> monitorInfo();
}
