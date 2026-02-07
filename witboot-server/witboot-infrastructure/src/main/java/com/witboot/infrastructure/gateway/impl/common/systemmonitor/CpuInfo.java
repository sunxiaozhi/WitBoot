package com.witboot.infrastructure.gateway.impl.common.systemmonitor;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * CPU 信息实体类，用于封装 CPU 相关的硬件和性能数据。
 */
@Setter
@Getter
public class CpuInfo implements Serializable {
    /**
     * CPU 名称
     */
    private String name;

    /**
     * CPU 厂商
     */
    private String vendor;

    /**
     * 物理 CPU 插槽数量
     */
    private int physicalPackageCount;

    /**
     * 物理处理器核心数量
     */
    private int physicalProcessorCount;

    /**
     * 逻辑处理器核心数量
     */
    private int logicalProcessorCount;

    /**
     * 最大频率（赫兹）
     */
    private long maximumFrequencyHertz;

    /**
     * 当前各核心频率列表（赫兹）
     */
    private List<Long> currentFrequenciesHertz;

    /**
     * 系统负载平均值列表
     */
    private List<Double> loadAverage;

    /**
     * CPU 使用率百分比
     */
    private double cpuUsagePercent;

    /**
     * CPU 使用率可读格式（例如："45.6%"）
     */
    private String cpuUsagePercentReadable;

    /**
     * 每个核心的 CPU 使用率百分比列表
     */
    private List<Double> perCoreUsagePercent;

    /**
     * 上下文切换次数增量
     */
    private long contextSwitchesDelta;

    /**
     * 中断次数增量
     */
    private long interruptsDelta;

    /**
     * 最大频率可读格式（例如："3.2 GHz"）
     */
    private String maximumFrequencyReadable;

    /**
     * 当前各核心频率可读格式列表（例如：["3.2 GHz", "2.8 GHz"]）
     */
    private List<String> currentFrequenciesReadable;
}
