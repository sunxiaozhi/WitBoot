package com.witboot.domain.department.gateway;

import com.witboot.client.department.dto.query.DepartmentListByParamQuery;
import com.witboot.domain.department.model.DepartmentEntity;

import java.util.List;

/**
 * Department Gateway
 *
 * @author sunxiaoizhi
 */
public interface DepartmentGateway {
    /**
     * 根据条件查询
     *
     * @param departmentListByParamQuery 部门名等
     * @return List 部门实体
     */
    List<DepartmentEntity> findByParam(DepartmentListByParamQuery departmentListByParamQuery);

    /**
     * 获取部门信息
     *
     * @param id 部门ID
     * @return 部门实体
     */
    DepartmentEntity findById(Long id);

    /**
     * 删除部门
     *
     * @param ids ids
     * @return 删除
     */
    int deleteByIds(String[] ids);
}
