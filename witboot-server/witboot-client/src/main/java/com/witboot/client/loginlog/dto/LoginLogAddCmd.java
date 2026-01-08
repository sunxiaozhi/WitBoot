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
