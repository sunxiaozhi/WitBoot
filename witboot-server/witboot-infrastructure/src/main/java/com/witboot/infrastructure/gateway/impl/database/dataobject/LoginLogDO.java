package com.witboot.infrastructure.gateway.impl.database.dataobject;

import lombok.Data;

/**
 * LoginLog Domain
 *
 * @author sunxiaoizhi
 */
@Data
public class  LoginLogDO {
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
     * 设备类型
     */
    private String deviceType;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * dl时间
     */
    private String loginTime;
}
