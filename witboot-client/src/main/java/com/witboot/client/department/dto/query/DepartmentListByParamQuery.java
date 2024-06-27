package com.witboot.client.department.dto.query;

import com.alibaba.cola.dto.Query;

import java.io.Serial;

/**
 * 查询部门列表
 *
 * @author sunxiaoizhi
 */
public class DepartmentListByParamQuery extends Query {

    @Serial
    private static final long serialVersionUID = -2794036665910524477L;

    private String name;
}
