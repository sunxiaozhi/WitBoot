package com.witboot.dao.mybatis.dataobject;

import lombok.Data;

/**
 * 用户信息数据对象
 *
 * @author sunxiaozhi
 */
@Data
public class UserDO {
    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;
}
