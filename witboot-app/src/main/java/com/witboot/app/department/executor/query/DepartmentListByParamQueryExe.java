package com.witboot.app.department.executor.query;

import com.witboot.app.department.assembler.DepartmentAssembler;
import com.witboot.client.department.dto.data.DepartmentVO;
import com.witboot.client.department.dto.query.DepartmentListByParamQuery;
import com.witboot.domain.department.gateway.DepartmentGateway;
import com.witboot.domain.department.model.DepartmentEntity;
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

    public List<DepartmentVO> execute(DepartmentListByParamQuery departmentListByParamQuery) {
        List<DepartmentEntity> departmentEntities = departmentGateway.findByParam(departmentListByParamQuery);

        return departmentEntities.stream()
                .map(DepartmentAssembler::toValueObject)
                .collect(Collectors.toList());
    }
}
