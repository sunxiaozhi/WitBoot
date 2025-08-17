package com.witboot.client.menu.dto.data;

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
public enum MenuErrorCode implements BaseErrorCode {
    /**
     * 尽量让code可以达意
     */
    B_MENU_UNDEFINED("B_MENU_UNDEFINED", "菜单不存在"),
    B_MENU_NAME_REPEAT("B_MENU_NAME_REPEAT", "菜单名称重复");

    /**
     * 错误码
     */
    private final String errorCode;

    /**
     * 错误描述
     */
    private final String errorDescription;
}
