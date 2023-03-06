package com.witboot.common.enums;

import lombok.Getter;

/**
 * 状态码枚举
 *
 * @author sunxiaozhi
 */
@Getter
public enum ResponseCode {
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
    private final int code;

    /**
     * 提示语
     */
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
