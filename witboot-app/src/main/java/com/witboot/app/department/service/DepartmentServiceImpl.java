package com.witboot.app.department.service;

import com.witboot.app.department.assembler.DepartmentAssembler;
import com.witboot.app.department.executor.command.DepartmentDeleteCmdExe;
import com.witboot.app.department.executor.query.DepartmentListByParamQueryExe;
import com.witboot.client.department.api.IDepartmentService;
import com.witboot.client.department.dto.data.DepartmentVO;
import com.witboot.client.department.dto.query.DepartmentListByParamQuery;
import com.witboot.domain.base.model.PageResult;
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
    private DepartmentListByParamQueryExe departmentListByParamQueryExe;

    @Autowired
    private DepartmentDeleteCmdExe departmentDeleteCmdExe;

    @Override
    public PageResult<DepartmentVO> listByParam(DepartmentListByParamQuery departmentListByParamQuery) {
        DepartmentQuerySpec departmentListByParamQuerySpec = DepartmentAssembler.toDepartmentListByParamQuerySpec(departmentListByParamQuery);
        return departmentListByParamQueryExe.execute(departmentListByParamQuerySpec);
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
