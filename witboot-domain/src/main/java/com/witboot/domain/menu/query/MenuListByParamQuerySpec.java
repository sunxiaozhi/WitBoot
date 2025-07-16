package com.witboot.domain.menu.query;

import lombok.Data;

/**
 * 查询菜单列表
 *
 * @author sunxiaoizhi
 */
@Data
public class MenuListByParamQuerySpec {
    /**
     * 菜单名称
     */
    private String name;

    private int pageNo;
    private int pageSize;
}
