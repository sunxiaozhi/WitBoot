package com.witboot.client.operationlog.dto.data;

import com.witboot.client.base.dto.data.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误信息
 *
 * @author sunxiaozhi
 */
@Getter
@AllArgsConstructor
public enum OperationLogErrorCode implements BaseErrorCode {
    /**
     * 尽量让 code 可以达意
     */
    B_OPERATION_LOG_UNDEFINED("B_OPERATION_LOG_UNDEFINED", "日志不存在");

    /**
     * 错误码
     */
    private final String errorCode;

    /**
     * 错误描述
     */
    private final String errorDescription;
}
