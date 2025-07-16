package com.witboot.domain.department.query;

import lombok.Data;

/**
 * 查询部门列表
 *
 * @author sunxiaoizhi
 */
@Data
public class DepartmentListByParamQuerySpec   {

    private String name;

    private int pageNo;
    private int pageSize;
}
