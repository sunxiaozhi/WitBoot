package com.witboot.infrastructure.gateway.impl.database.dataobject;

import lombok.Data;

/**
 * OperationLog Domain
 *
 * @author sunxiaoizhi
 */
@Data
public class OperationLogDo {
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
