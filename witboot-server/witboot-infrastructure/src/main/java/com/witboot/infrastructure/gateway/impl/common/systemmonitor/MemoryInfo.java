package com.witboot.infrastructure.gateway.impl.common.systemmonitor;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 内存信息实体类，用于封装系统内存的使用情况及相关数据。
 */
@Setter
@Getter
public class MemoryInfo implements Serializable {

    /**
     * 总内存大小（字节）
     */
    private long totalBytes;

    /**
     * 已使用内存大小（字节）
     */
    private long usedBytes;

    /**
     * 可用内存大小（字节）
     */
    private long availableBytes;

    /**
     * 内存使用率百分比（0-100）
     */
    private double usedPercent;

    /**
     * 总内存大小可读格式（例如："8 GB"）
     */
    private String totalReadable;

    /**
     * 已使用内存大小可读格式（例如："4 GB"）
     */
    private String usedReadable;

    /**
     * 可用内存大小可读格式（例如："4 GB"）
     */
    private String availableReadable;

    /**
     * 内存使用率百分比可读格式（例如："50%"）
     */
    private String usedPercentReadable;

    /**
     * 交换内存（Swap）信息
     */
    private SwapInfo swap;
}
