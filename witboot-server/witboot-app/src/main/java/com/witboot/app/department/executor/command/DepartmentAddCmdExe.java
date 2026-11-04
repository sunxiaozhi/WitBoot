package com.witboot.app.department.executor.command;

import com.witboot.app.department.assembler.DepartmentAssembler;
import com.witboot.client.department.dto.DepartmentAddCmd;
import com.witboot.client.department.dto.data.DepartmentVO;
import com.witboot.domain.department.gateway.DepartmentGateway;
import com.witboot.domain.department.model.DepartmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DepartmentRegisterCmdExe
 *
 * @author sunxiaoizhi
 */
@Component
public class DepartmentAddCmdExe {

    @Autowired
    private DepartmentGateway departmentGateway;

    public DepartmentVO execute(DepartmentAddCmd departmentAddCmd) {
        DepartmentEntity departmentEntity = departmentGateway.save(DepartmentAssembler.toEntity(departmentAddCmd));

        return DepartmentAssembler.toValueObject(departmentEntity);
    }
}
