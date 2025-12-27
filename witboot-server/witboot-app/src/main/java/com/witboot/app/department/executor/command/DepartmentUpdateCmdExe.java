package com.witboot.app.department.executor.command;

import com.witboot.domain.department.gateway.DepartmentGateway;
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

//    public DepartmentVO execute(DepartmentModifyCmd departmentModifyCmd) {
//        // check 用户名是否重复
//        if (departmentGateway.checkByDepartmentname(departmentModifyCmd.getId(), departmentModifyCmd.getDepartmentname())) {
//            throw new WitBootBizException(ErrorCode.B_USER_USERNAME_REPEAT);
//        }
//
//        DepartmentEntity departmentEntity = departmentGateway.save(DepartmentAssembler.toEntity(departmentModifyCmd));
//        return DepartmentAssembler.toValueObject(departmentEntity);
//    }
}
