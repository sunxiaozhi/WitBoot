package com.witboot.infrastructure.gateway.impl.database.dataobject;

import lombok.Data;

/**
 * OperationLog Domain
 *
 * @author sunxiaoizhi
 */
@Data
public class OperationLogDO {
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
