package com.witboot.client.department.dto.query;

import com.witboot.client.base.model.query.BaseQuery;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询部门列表
 *
 * @author sunxiaoizhi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class DepartmentListByParamQuery extends BaseQuery {
    private String name;
}
