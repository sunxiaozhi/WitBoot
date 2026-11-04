package com.witboot.app.department.executor.command;

import com.witboot.app.department.assembler.DepartmentAssembler;
import com.witboot.client.department.dto.DepartmentUpdateCmd;
import com.witboot.client.department.dto.data.DepartmentVO;
import com.witboot.domain.department.gateway.DepartmentGateway;
import com.witboot.domain.department.model.DepartmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DepartmentModifyCmdExe
 *
 * @author sunxiaoizhi
 */
@Component
public class DepartmentUpdateCmdExe {
    @Autowired
    private DepartmentGateway departmentGateway;

    public DepartmentVO execute(DepartmentUpdateCmd departmentUpdateCmd) {
        DepartmentEntity departmentEntity = departmentGateway.save(DepartmentAssembler.toEntity(departmentUpdateCmd));
        return DepartmentAssembler.toValueObject(departmentEntity);
    }
}
