package com.witboot.client.loginlog.dto;

import com.alibaba.cola.dto.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 新增登录日志请求
 *
 * @author sunxiaoizhi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginLogAddCmd extends Command {

    @Serial
    private static final long serialVersionUID = -5726685703640910355L;
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
