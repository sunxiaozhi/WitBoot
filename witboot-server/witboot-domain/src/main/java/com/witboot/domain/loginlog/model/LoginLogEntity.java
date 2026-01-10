package com.witboot.domain.loginlog.model;

import lombok.Data;

/**
 * LoginLog Domain
 *
 * @author sunxiaoizhi
 */
@Data
public class LoginLogEntity {
    /**
     * id
     */
    private Long id;

    /**
     * ip
     */
    private String ip;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 位置
     */
    private String location;

    /**
     * user-agent
     */
    private String userAgent;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 设备类型
     */
    private String device;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * dl时间
     */
    private String loginTime;
}
