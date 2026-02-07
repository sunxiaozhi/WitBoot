package com.witboot.infrastructure.gateway.impl.common.systemmonitor;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 交换内存（Swap）信息实体类，用于封装系统交换内存的使用情况。
 */
@Setter
@Getter
public class SwapInfo implements Serializable {

    /**
     * 交换内存总大小（字节）
     */
    private long totalBytes;

    /**
     * 已使用交换内存大小（字节）
     */
    private long usedBytes;

    /**
     * 交换内存使用率百分比（0-100）
     */
    private double usedPercent;

    /**
     * 交换内存总大小可读格式（例如："2 GB"）
     */
    private String totalReadable;

    /**
     * 已使用交换内存大小可读格式（例如："1 GB"）
     */
    private String usedReadable;

    /**
     * 交换内存使用率百分比可读格式（例如："50%"）
     */
    private String usedPercentReadable;
}
