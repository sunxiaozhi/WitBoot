package com.witboot.infrastructure.gateway.impl.common.systemmonitor;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 网络接口基础信息实体类，用于封装网络接口的基本配置和统计数据。
 */
@Setter
@Getter
public class NetworkInterfaceBasic implements Serializable {

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
     * 最大传输单元（MTU）
     */
    private long maximumTransmissionUnit;

    /**
     * 接口速度（比特每秒）
     */
    private long speedBitsPerSecond;

    /**
     * 总接收字节数
     */
    private long totalReceivedBytes;

    /**
     * 总发送字节数
     */
    private long totalTransmittedBytes;

    /**
     * 接口速度可读格式（例如："1 Gbps"）
     */
    private String speedReadable;

    /**
     * 总接收数据量可读格式（例如："100 MB"）
     */
    private String totalReceivedReadable;

    /**
     * 总发送数据量可读格式（例如："50 MB"）
     */
    private String totalTransmittedReadable;
}
