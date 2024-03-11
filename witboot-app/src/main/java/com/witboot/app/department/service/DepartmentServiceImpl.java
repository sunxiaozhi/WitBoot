package com.witboot.app.department.service;

import com.alibaba.cola.dto.MultiResponse;
import com.witboot.client.department.api.IDepartmentService;
import com.witboot.client.department.dto.data.DepartmentVo;
import com.witboot.client.department.dto.query.DepartmentParamQuery;
import org.springframework.stereotype.Service;

/**
 * 部门相关
 *
 * @author sunxiaoizhi
 */
@Service
public class DepartmentServiceImpl implements IDepartmentService {
    @Override
    public MultiResponse<DepartmentVo> listByParam(DepartmentParamQuery departmentParamQuery) {
        return null;
    }
}
