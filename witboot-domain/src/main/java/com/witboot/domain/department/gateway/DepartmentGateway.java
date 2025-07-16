package com.witboot.domain.department.gateway;

import com.witboot.domain.base.model.PageResult;
import com.witboot.domain.department.model.DepartmentEntity;
import com.witboot.domain.department.query.DepartmentListByParamQuerySpec;

/**
 * Department Gateway
 *
 * @author sunxiaoizhi
 */
public interface DepartmentGateway {
    /**
     * 根据条件查询
     *
     * @param departmentListByParamQuerySpec 部门名等
     * @return List 部门实体
     */
    PageResult<DepartmentEntity> findByParam(DepartmentListByParamQuerySpec departmentListByParamQuerySpec);

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
