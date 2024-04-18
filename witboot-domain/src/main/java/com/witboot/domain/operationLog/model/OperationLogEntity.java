package com.witboot.domain.operationLog.model;

import lombok.Data;

/**
 * OperationLog Domain
 *
 * @author sunxiaoizhi
 */
@Data
public class OperationLogEntity {
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
     * x响应状态码
     */
    private int status;

    /**
     * 请求内容
     */
    private String requestContent;

    /**
     * 响应内容
     */
    private String responseContent;
}
