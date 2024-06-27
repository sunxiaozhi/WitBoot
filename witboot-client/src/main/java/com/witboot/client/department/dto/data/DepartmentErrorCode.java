package com.witboot.client.department.dto.data;

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
public enum DepartmentErrorCode implements BaseErrorCode {
    /**
     * 尽量让code可以达意
     */
    B_NAME_REPEAT("B_USER_USERNAME_REPEAT", "用户名重复"),
    B_DEPARTMENT_UNDEFINED("B_USER_UNDEFINED", "用户不存在");

    /**
     * 错误码
     */
    private final String errorCode;

    /**
     * 错误描述
     */
    private final String errorDescription;
}
