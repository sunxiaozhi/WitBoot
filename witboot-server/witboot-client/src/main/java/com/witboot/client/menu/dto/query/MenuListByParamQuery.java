package com.witboot.client.menu.dto.query;

import com.witboot.domain.base.model.query.BaseQuery;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询菜单列表
 *
 * @author sunxiaoizhi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class MenuListByParamQuery extends BaseQuery {
    /**
     * 菜单名称
     */
    private String name;
}
