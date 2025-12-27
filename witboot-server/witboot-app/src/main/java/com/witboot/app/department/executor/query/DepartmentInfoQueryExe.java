package com.witboot.app.department.executor.query;

import com.witboot.app.department.assembler.DepartmentAssembler;
import com.witboot.client.department.dto.data.DepartmentErrorCode;
import com.witboot.client.department.dto.data.DepartmentVO;
import com.witboot.domain.department.gateway.DepartmentGateway;
import com.witboot.domain.department.model.DepartmentEntity;
import com.witboot.infrastructure.common.exception.WitBootBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 用户信息查询
 *
 * @author sunxiaoizhi
 */
@Component
public class DepartmentInfoQueryExe {
    @Autowired
    private DepartmentGateway departmentGateway;

    public DepartmentVO execute(Long id) {
        DepartmentEntity departmentEntity = departmentGateway.findById(id);
        if (Objects.isNull(departmentEntity)) {
            throw new WitBootBizException(DepartmentErrorCode.B_DEPARTMENT_UNDEFINED);
        }

        return DepartmentAssembler.toValueObject(departmentEntity);
    }
}
