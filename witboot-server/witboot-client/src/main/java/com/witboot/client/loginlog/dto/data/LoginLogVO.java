package com.witboot.client.loginlog.dto.data;

import lombok.Data;

/**
 * LoginLog VO
 *
 * @author sunxiaoizhi
 */
@Data
public class LoginLogVO {
    /**
     * id
     */
    private Long id;

    /**
     * ip
     */
    private String ip;

    /**
     * 位置
     */
    private String location;

    /**
     * 方法
     */
    private String method;

    /**
     * uri
     */
    private String uri;

    /**
     * 请求时间
     */
    private String requestTime;

    /**
     * 耗费时间
     */
    private long wasteTime;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 请求参数
     */
    private String requestBody;

    /**
     * 响应内容
     */
    private String responseResult;
}
