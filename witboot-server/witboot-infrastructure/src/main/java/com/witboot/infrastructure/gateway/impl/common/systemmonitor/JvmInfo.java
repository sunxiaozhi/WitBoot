package com.witboot.infrastructure.gateway.impl.common.systemmonitor;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

/**
 * JVM 信息实体类，用于封装 Java 虚拟机的相关运行时数据。
 */
@Setter
@Getter
public class JvmInfo implements Serializable {

    /**
     * Java 版本
     */
    private String javaVersion;

    /**
     * Java 供应商
     */
    private String javaVendor;

    /**
     * Java 安装路径
     */
    private String javaHome;

    /**
     * Java 虚拟机名称
     */
    private String javaVirtualMachineName;

    /**
     * Java 虚拟机版本
     */
    private String javaVirtualMachineVersion;

    /**
     * 进程 ID 和主机信息（格式：PID@HOSTNAME）
     */
    private String processIdAtHost;

    /**
     * JVM 启动以来的运行时间（毫秒）
     */
    private long uptimeMilliseconds;

    /**
     * JVM 启动时间
     */
    private Instant startTime;

    /**
     * 当前堆内存已使用大小（字节）
     */
    private long heapUsedBytes;

    /**
     * 当前堆内存总分配大小（字节）
     */
    private long heapTotalBytes;

    /**
     * 堆内存最大可用大小（字节）
     */
    private long heapMaxBytes;

    /**
     * 堆内存使用率占最大值的百分比
     */
    private double heapUsedPercentOfMax;

    /**
     * 堆内存已使用大小可读格式（例如："128 MB"）
     */
    private String heapUsedReadable;

    /**
     * 堆内存总分配大小可读格式（例如："256 MB"）
     */
    private String heapTotalReadable;

    /**
     * 堆内存最大可用大小可读格式（例如："512 MB"）
     */
    private String heapMaxReadable;

    /**
     * 堆内存使用率占最大值的百分比可读格式（例如："25%"）
     */
    private String heapUsedPercentOfMaxReadable;

    /**
     * 当前线程总数
     */
    private int threadCount;

    /**
     * 当前后台线程（守护线程）数量
     */
    private int daemonThreadCount;

    /**
     * 已加载的类数量
     */
    private int loadedClassCount;
}
