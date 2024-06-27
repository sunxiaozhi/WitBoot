package com.witboot.app.department.service;

import com.witboot.app.department.executor.command.DepartmentDeleteCmdExe;
import com.witboot.app.department.executor.query.DepartmentListByParamQueryExe;
import com.witboot.client.department.api.IDepartmentService;
import com.witboot.client.department.dto.data.DepartmentVO;
import com.witboot.client.department.dto.query.DepartmentListByParamQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<DepartmentVO> listByParam(DepartmentListByParamQuery departmentListByParamQuery) {
        return departmentListByParamQueryExe.execute(departmentListByParamQuery);
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
