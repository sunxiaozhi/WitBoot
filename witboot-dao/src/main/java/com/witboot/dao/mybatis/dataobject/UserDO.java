package com.witboot.dao.mybatis.dataobject;

import lombok.Data;

/**
 * 用户信息数据对象
 *
 * @author sunxiaozhi
 */
@Data
public class UserDO {
    private Long id;

    private String name;

    private Integer age;
}
