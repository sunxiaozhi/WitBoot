package com.witboot.app.department.service;

import com.witboot.client.department.api.IDepartmentService;
import com.witboot.client.department.dto.data.DepartmentVo;
import com.witboot.client.department.dto.query.DepartmentParamQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门相关
 *
 * @author sunxiaoizhi
 */
@Service
public class DepartmentServiceImpl implements IDepartmentService {
    @Override
    public List<DepartmentVo> listByParam(DepartmentParamQuery departmentParamQuery) {
        return null;
    }
}
