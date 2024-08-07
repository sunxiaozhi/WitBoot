package com.witboot.client.user.dto.data;

import com.witboot.client.base.dato.data.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误信息
 *
 * @author sunxiaozhi
 */
@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
    /**
     * 尽量让code可以达意
     */
    B_USER_USERNAME_REPEAT("B_USER_USERNAME_REPEAT", "用户名重复"),
    B_USER_UNDEFINED("B_USER_UNDEFINED", "用户不存在"),
    B_USER_PASSWORD_ERROR("B_USER_PASSWORD_ERROR", "用户名或密码不正确"),
    B_USER_AUTH_TOKEN_ERROR("B_USER_AUTH_TOKEN_ERROR", "令牌失效，请重新登录");

    /**
     * 错误码
     */
    private final String errorCode;

    /**
     * 错误描述
     */
    private final String errorDescription;
}
