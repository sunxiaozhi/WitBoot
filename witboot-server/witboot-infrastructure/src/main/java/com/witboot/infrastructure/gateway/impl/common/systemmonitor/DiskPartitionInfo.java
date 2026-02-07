package com.witboot.infrastructure.gateway.impl.common.systemmonitor;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 磁盘分区信息实体类，用于封装磁盘分区的基本信息和使用情况。
 */
@Setter
@Getter
public class DiskPartitionInfo implements Serializable {

    /**
     * 分区名称
     */
    private String name;

    /**
     * 挂载点路径
     */
    private String mount;

    /**
     * 文件系统类型（例如：NTFS、ext4 等）
     */
    private String type;

    /**
     * 卷标名称
     */
    private String volume;

    /**
     * 分区总大小（字节）
     */
    private long totalBytes;

    /**
     * 已使用空间（字节）
     */
    private long usedBytes;

    /**
     * 剩余可用空间（字节）
     */
    private long freeBytes;

    /**
     * 使用率百分比（0-100）
     */
    private double usedPercent;

    /**
     * 分区总大小可读格式（例如："500 GB"）
     */
    private String totalReadable;

    /**
     * 已使用空间可读格式（例如："300 GB"）
     */
    private String usedReadable;

    /**
     * 剩余可用空间可读格式（例如："200 GB"）
     */
    private String freeReadable;

    /**
     * 使用率百分比可读格式（例如："60%"）
     */
    private String usedPercentReadable;
}
