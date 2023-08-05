package com.witboot.domain.user;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ValidationException;

/**
 * 用户名称
 *
 * @author sunxiaoizhi
 */
public class UserName {

    private final String name;

    public UserName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new ValidationException("用户名不能为空");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
