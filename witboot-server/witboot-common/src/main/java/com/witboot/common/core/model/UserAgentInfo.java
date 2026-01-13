package com.witboot.common.core.model;

import lombok.Data;

@Data
public class UserAgentInfo {

    /** 浏览器 */
    private String browser;

    /** 操作系统 */
    private String os;

    /** 设备类型：PC / Mobile / Tablet */
    private String device;
}

