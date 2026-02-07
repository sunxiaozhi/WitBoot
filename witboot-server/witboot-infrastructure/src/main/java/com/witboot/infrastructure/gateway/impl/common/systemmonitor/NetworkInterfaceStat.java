package com.witboot.infrastructure.gateway.impl.common.systemmonitor;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 网络接口统计信息实体类，用于封装网络接口的实时流量和性能数据。
 */
@Setter
@Getter
public class NetworkInterfaceStat implements Serializable {

    /**
     * 网络接口名称
     */
    private String name;

    /**
     * 网络接口显示名称
     */
    private String displayName;

    /**
     * MAC 地址
     */
    private String macAddress;

    /**
     * IPv4 地址列表
     */
    private List<String> ipv4Addresses;

    /**
     * IPv6 地址列表
     */
    private List<String> ipv6Addresses;

    /**
     * 接口速度（比特每秒）
     */
    private long speedBitsPerSecond;

    /**
     * 接口速度可读格式（例如："1 Gbps"）
     */
    private String speedReadable;

    /**
     * 每秒接收字节数
     */
    private double receiveBytesPerSecond;

    /**
     * 每秒发送字节数
     */
    private double transmitBytesPerSecond;

    /**
     * 每秒接收数据量可读格式（例如："10 MB/s"）
     */
    private String receiveReadablePerSecond;

    /**
     * 每秒发送数据量可读格式（例如："5 MB/s"）
     */
    private String transmitReadablePerSecond;

    /**
     * 每秒接收数据包数
     */
    private double receivePacketsPerSecond;

    /**
     * 每秒发送数据包数
     */
    private double transmitPacketsPerSecond;
}
