package com.witboot.app.department.service;

import com.witboot.app.department.assembler.DepartmentAssembler;
import com.witboot.app.department.executor.command.DepartmentAddCmdExe;
import com.witboot.app.department.executor.command.DepartmentDeleteCmdExe;
import com.witboot.app.department.executor.command.DepartmentUpdateCmdExe;
import com.witboot.app.department.executor.query.DepartmentListByParamQueryExe;
import com.witboot.client.department.api.IDepartmentService;
import com.witboot.client.department.dto.DepartmentAddCmd;
import com.witboot.client.department.dto.DepartmentUpdateCmd;
import com.witboot.client.department.dto.data.DepartmentVO;
import com.witboot.client.department.dto.query.DepartmentListByParamQuery;
import com.witboot.common.core.model.PageResult;
import com.witboot.domain.department.query.DepartmentQuerySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 部门相关
 *
 * @author sunxiaoizhi
 */
@Service
public class DepartmentServiceImpl implements IDepartmentService {
    @Autowired
    private DepartmentAddCmdExe departmentAddCmdExe;

    @Autowired
    private DepartmentUpdateCmdExe departmentUpdateCmdExe;

    @Autowired
    private DepartmentListByParamQueryExe departmentListByParamQueryExe;

    @Autowired
    private DepartmentDeleteCmdExe departmentDeleteCmdExe;

    @Override
    public PageResult<DepartmentVO> listByParam(DepartmentListByParamQuery departmentListByParamQuery) {
        DepartmentQuerySpec departmentListByParamQuerySpec = DepartmentAssembler.toDepartmentListByParamQuerySpec(departmentListByParamQuery);
        return departmentListByParamQueryExe.execute(departmentListByParamQuerySpec);
    }

    @Override
    public DepartmentVO add(DepartmentAddCmd departmentAddCmd) {
        return departmentAddCmdExe.execute(departmentAddCmd);
    }

    @Override
    public DepartmentVO update(DepartmentUpdateCmd departmentUpdateCmd) {
        return departmentUpdateCmdExe.execute(departmentUpdateCmd);
    }

    /**
     * 用户删除
     * @param ids ids
     * @return 删除数量
     */
    @Override
    public int deleteByIds(String[] ids){
        return departmentDeleteCmdExe.execute(ids);
    }
}
