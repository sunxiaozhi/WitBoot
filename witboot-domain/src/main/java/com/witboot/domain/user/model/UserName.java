package com.witboot.domain.user.model;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ValidationException;

/**
 * 用户名称
 *
 * @author sunxiaoizhi
 */
public record UserName(String name) {
    public UserName {
        if (StringUtils.isBlank(name)) {
            throw new ValidationException("用户名不能为空");
        }
    }
}
