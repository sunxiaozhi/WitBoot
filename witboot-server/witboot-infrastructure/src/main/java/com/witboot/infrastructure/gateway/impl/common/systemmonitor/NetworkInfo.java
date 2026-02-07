package com.witboot.infrastructure.gateway.impl.common.systemmonitor;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 网络信息实体类，用于封装网络接口的统计信息。
 */
@Setter
@Getter
public class NetworkInfo implements Serializable {

    /**
     * 采样间隔时间（毫秒）
     */
    private int sampleIntervalMilliseconds;

    /**
     * 网络接口统计信息列表
     */
    private List<NetworkInterfaceStat> interfaces;
}
