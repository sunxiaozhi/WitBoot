package com.witboot.app.department.executor.query;

import com.witboot.app.department.assembler.DepartmentAssembler;
import com.witboot.client.department.dto.data.DepartmentVO;
import com.witboot.domain.base.model.PageResult;
import com.witboot.domain.department.gateway.DepartmentGateway;
import com.witboot.domain.department.model.DepartmentEntity;
import com.witboot.domain.department.query.DepartmentListByParamQuerySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DepartmentListByParamQueryExe
 *
 * @author sunxiaoizhi
 */
@Component
public class DepartmentListByParamQueryExe {

    @Autowired
    private DepartmentGateway departmentGateway;

    public PageResult<DepartmentVO> execute(DepartmentListByParamQuerySpec departmentListByParamQuerySpec) {
        PageResult<DepartmentEntity> departmentEntityPageResult = departmentGateway.findByParam(departmentListByParamQuerySpec);

        List<DepartmentVO> departmentVOList = departmentEntityPageResult.getList().stream()
                .map(DepartmentAssembler::toValueObject)
                .collect(Collectors.toList());

        return PageResult.build(departmentVOList, departmentEntityPageResult.getTotal());
    }
}
