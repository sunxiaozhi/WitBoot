package com.witboot.infrastructure.gateway.impl.common.systemmonitor;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

/**
 * 监控快照实体类，用于封装某一时刻系统的各项监控数据。
 */
@Setter
@Getter
public class MonitorSnapshot implements Serializable {

    /**
     * 快照时间戳
     */
    private Instant timestamp;

    /**
     * CPU 信息
     */
    private CpuInfo cpuInfo;

    /**
     * 内存信息
     */
    private MemoryInfo memoryInfo;

    /**
     * 磁盘信息
     */
    private DiskInfo diskInfo;

    /**
     * 网络信息
     */
    private NetworkInfo networkInfo;

    /**
     * JVM 信息
     */
    private JvmInfo jvmInfo;

    /**
     * 服务器信息
     */
    private ServerInfo serverInfo;
}
