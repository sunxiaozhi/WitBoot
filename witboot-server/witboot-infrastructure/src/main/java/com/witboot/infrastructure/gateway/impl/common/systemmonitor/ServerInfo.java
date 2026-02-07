package com.witboot.infrastructure.gateway.impl.common.systemmonitor;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * 服务器信息实体类，用于封装服务器硬件和操作系统的基本信息。
 */
@Setter
@Getter
public class ServerInfo implements Serializable {

    /**
     * 操作系统家族（例如：Windows、Linux、macOS）
     */
    private String operatingSystemFamily;

    /**
     * 操作系统版本
     */
    private String operatingSystemVersion;

    /**
     * 操作系统构建号
     */
    private String operatingSystemBuildNumber;

    /**
     * 系统架构（例如：x86、x64、ARM）
     */
    private String architecture;

    /**
     * 系统制造商
     */
    private String systemManufacturer;

    /**
     * 系统型号
     */
    private String systemModel;

    /**
     * 系统序列号
     */
    private String systemSerialNumber;

    /**
     * 固件名称
     */
    private String firmwareName;

    /**
     * 系统启动时间
     */
    private Instant bootTime;

    /**
     * 主机名
     */
    private String hostName;

    /**
     * 主机地址（IP 地址）
     */
    private String hostAddress;

    /**
     * 网络接口基本信息列表
     */
    private List<NetworkInterfaceBasic> networkInterfaces;
}
