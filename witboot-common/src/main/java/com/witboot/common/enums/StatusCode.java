package com.witboot.common.enums;

import lombok.Getter;

/**
 * 状态码枚举
 *
 * @author sunxiaozhi
 */
@Getter
public enum StatusCode {
    /**
     * 成功
     */
    SUCCESS(200, "成功"),

    /**
     * 失败
     */
    FAIL(-200, "失败");

    /**
     * 状态码
     */
    private final int statusCode;

    /**
     * 提示语
     */
    private final String message;

    StatusCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
