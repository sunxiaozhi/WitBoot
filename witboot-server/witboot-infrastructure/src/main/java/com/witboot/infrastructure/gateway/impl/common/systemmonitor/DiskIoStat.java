package com.witboot.infrastructure.gateway.impl.common.systemmonitor;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 磁盘IO统计信息实体类，用于封装磁盘的输入输出性能数据。
 */
@Setter
@Getter
public class DiskIoStat implements Serializable {

    /**
     * 磁盘名称
     */
    private String name;

    /**
     * 磁盘型号
     */
    private String model;

    /**
     * 磁盘序列号
     */
    private String serial;

    /**
     * 磁盘总大小（单位：字节）
     */
    private long size;

    /**
     * 每秒读取字节数（单位：bytes/s）
     */
    private double readBytesPerSec;

    /**
     * 每秒写入字节数（单位：bytes/s）
     */
    private double writeBytesPerSec;

    /**
     * 每秒读操作次数（IOPS，单位：ops/s）
     */
    private double readIops;

    /**
     * 每秒写操作次数（IOPS，单位：ops/s）
     */
    private double writeIops;

    /**
     * 每秒传输操作次数（TPS，单位：transfers/s）
     */
    private double tps;

    /**
     * 当前队列长度（部分平台支持）
     */
    private long currentQueueLength;

    /**
     * 总传输时间（单位：毫秒）
     */
    private long transferTimeMsTotal;
}
