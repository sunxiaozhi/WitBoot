package com.witboot.app.department.executor.command;

import com.witboot.domain.department.gateway.DepartmentGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DepartmentRegisterCmdExe
 *
 * @author sunxiaoizhi
 */
@Component
public class DepartmentRegisterCmdExe {

    @Autowired
    private DepartmentGateway departmentGateway;

//    public DepartmentVO execute(DepartmentRegisterCmd departmentRegisterCmd) {
//        // check 用户名是否重复
//        if (departmentGateway.checkByDepartmentname(null, departmentRegisterCmd.getDepartmentname())) {
//            throw new WitBootBizException(ErrorCode.B_USER_USERNAME_REPEAT);
//        }
//
//        DepartmentEntity departmentEntity = departmentGateway.save(DepartmentAssembler.toEntity(departmentRegisterCmd));
//
//        return DepartmentAssembler.toValueObject(departmentEntity);
//    }
}
