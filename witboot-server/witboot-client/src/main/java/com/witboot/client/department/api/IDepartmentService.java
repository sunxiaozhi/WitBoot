package com.witboot.client.department.api;

import com.witboot.client.department.dto.DepartmentAddCmd;
import com.witboot.client.department.dto.DepartmentUpdateCmd;
import com.witboot.client.department.dto.data.DepartmentVO;
import com.witboot.client.department.dto.query.DepartmentListByParamQuery;
import com.witboot.common.core.model.PageResult;

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
    PageResult<DepartmentVO> listByParam(DepartmentListByParamQuery departmentParamQuery);

    /**
     * 新增部门
     *
     * @param departmentAddCmd 部门新增请求
     * @return 部门信息
     */
    DepartmentVO add(DepartmentAddCmd departmentAddCmd);

    /**
     * 部门信息修改
     *
     * @param departmentUpdateCmd 部门信息修改请求
     * @return 部门信息
     */
    DepartmentVO update(DepartmentUpdateCmd departmentUpdateCmd);

    /**
     * 部门删除
     * @param ids ids
     * @return 删除数量
     */
    int deleteByIds(String[] ids);
}
