package com.witboot.app.department.executor.command;

import com.witboot.domain.department.gateway.DepartmentGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DepartmentDeleteCmdExe
 *
 * @author sunxiaoizhi
 */
@Component
public class DepartmentDeleteCmdExe {
    @Autowired
    private DepartmentGateway departmentGateway;
    public int execute(String[] ids) {
        return departmentGateway.deleteByIds(ids);
    }
}
