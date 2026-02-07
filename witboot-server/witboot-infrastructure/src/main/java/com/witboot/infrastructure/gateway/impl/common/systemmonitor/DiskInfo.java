package com.witboot.infrastructure.gateway.impl.common.systemmonitor;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 磁盘信息实体类，用于封装磁盘分区及输入输出统计相关数据。
 */
@Setter
@Getter
public class DiskInfo implements Serializable {

    /**
     * 磁盘分区信息列表
     */
    private List<DiskPartitionInfo> partitions;

    /**
     * 输入输出采样间隔时间（毫秒）
     */
    private int inputOutputSampleIntervalMilliseconds;

    /**
     * 磁盘输入输出统计信息列表
     */
    private List<DiskInputOutputStat> inputOutputStatistics;
}
