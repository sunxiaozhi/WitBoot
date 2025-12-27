package com.witboot.domain.department.query;

import com.witboot.domain.base.model.query.BaseQuerySpec;
import lombok.Getter;
import lombok.Setter;

/**
 * 查询部门
 *
 * @author sunxiaoizhi
 */
@Getter
@Setter
public class DepartmentQuerySpec extends BaseQuerySpec {
    /**
     * 部门名称
     */
    private String name;
}
