package com.witboot.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户查询类
 *
 * @author sunxiaozhi
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Data
public class UserPageQuery implements Serializable {
    /**
     * 用户名
     */
    private String name;
}
