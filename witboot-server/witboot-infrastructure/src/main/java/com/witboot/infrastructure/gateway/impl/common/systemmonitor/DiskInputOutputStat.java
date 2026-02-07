package com.witboot.infrastructure.gateway.impl.common.systemmonitor;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 磁盘输入输出统计信息实体类，用于封装磁盘的读写性能数据。
 */
@Setter
@Getter
public class DiskInputOutputStat implements Serializable {

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
     * 磁盘总大小（字节）
     */
    private long sizeBytes;

    /**
     * 磁盘总大小可读格式（例如："500 GB"）
     */
    private String sizeReadable;

    /**
     * 每秒读取字节数
     */
    private double readBytesPerSecond;

    /**
     * 每秒写入字节数
     */
    private double writeBytesPerSecond;

    /**
     * 每秒读取数据量可读格式（例如："10 MB/s"）
     */
    private String readReadablePerSecond;

    /**
     * 每秒写入数据量可读格式（例如："5 MB/s"）
     */
    private String writeReadablePerSecond;

    /**
     * 每秒读操作次数
     */
    private double readOperationsPerSecond;

    /**
     * 每秒写操作次数
     */
    private double writeOperationsPerSecond;

    /**
     * 每秒传输操作次数
     */
    private double transfersPerSecond;

    /**
     * 当前队列长度
     */
    private long currentQueueLength;

    /**
     * 总传输时间（毫秒）
     */
    private long transferTimeMillisecondsTotal;
}
