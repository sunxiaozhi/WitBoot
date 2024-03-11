package com.witboot.client.department.api;

import com.alibaba.cola.dto.MultiResponse;
import com.witboot.client.department.dto.data.DepartmentVo;
import com.witboot.client.department.dto.query.DepartmentParamQuery;

/**
 * 部门相关
 *
 * @author sunxiaoizhi
 */
public interface IDepartmentService {
    /**
     * 根据部门名称查询
     *
     * @param departmentParamQuery 部门查询请求
     * @return MultiResponse
     */
    MultiResponse<DepartmentVo> listByParam(DepartmentParamQuery departmentParamQuery);
}
