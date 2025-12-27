package com.witboot.domain.menu.query;

import com.witboot.domain.base.model.query.BaseQuerySpec;
import lombok.Getter;
import lombok.Setter;

/**
 * 查询菜单
 *
 * @author sunxiaoizhi
 */
@Getter
@Setter
public class MenuQuerySpec extends BaseQuerySpec {
    /**
     * 菜单名称
     */
    private String name;
}
