package com.witboot.common.core.dto.data;

/**
 * 错误信息
 *
 * @author sunxiaozhi
 */
public interface BaseErrorCode {
    /**
     * 获取错误码
     * @return 错误码
     */
    String getErrorCode();

    /**
     * 获取错误描述
     * @return 错误描述
     */
    String getErrorDescription();
}
