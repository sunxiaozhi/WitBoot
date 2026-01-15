package com.witboot.app.department.assembler;

import com.witboot.client.department.dto.data.DepartmentVO;
import com.witboot.client.department.dto.query.DepartmentListByParamQuery;
import com.witboot.domain.department.model.DepartmentEntity;
import com.witboot.domain.department.query.DepartmentQuerySpec;
import org.springframework.beans.BeanUtils;

/**
 * Department Application层转换器
 * 用于DTO与实体之间的互转
 *
 * @author sunxiaozhi
 */
public class DepartmentAssembler {
    public static DepartmentQuerySpec toDepartmentListByParamQuerySpec (DepartmentListByParamQuery departmentListByParamQuery) {
        DepartmentQuerySpec departmentListByParamQuerySpec = new DepartmentQuerySpec();
        BeanUtils.copyProperties(departmentListByParamQuery, departmentListByParamQuerySpec);
        return departmentListByParamQuerySpec;
    }

    public static DepartmentVO toValueObject(DepartmentEntity departmentEntity) {
        DepartmentVO departmentVO = new DepartmentVO();
        BeanUtils.copyProperties(departmentEntity, departmentVO);
        return departmentVO;
    }
}
