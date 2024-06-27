package com.witboot.infrastructure.gateway.impl;

import com.github.pagehelper.PageInfo;
import com.witboot.client.department.dto.data.DepartmentErrorCode;
import com.witboot.client.department.dto.query.DepartmentListByParamQuery;
import com.witboot.domain.department.gateway.DepartmentGateway;
import com.witboot.domain.department.model.DepartmentEntity;
import com.witboot.infrastructure.common.exception.WitBootBizException;
import com.witboot.infrastructure.convertor.DepartmentConvertor;
import com.witboot.infrastructure.gateway.impl.database.dataobject.DepartmentDO;
import com.witboot.infrastructure.gateway.impl.database.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DepartmentGatewayImpl
 *
 * @author sunxiaoizhi
 */
@Component("departmentGateway")
public class DepartmentGatewayImpl implements DepartmentGateway {
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<DepartmentEntity> findByParam(DepartmentListByParamQuery query) {
        List<DepartmentEntity> departmentEntityList = new ArrayList<>();

        List<DepartmentDO> departmentDOList = departmentMapper.selectByParam(query);

        new PageInfo<>(departmentDOList).getTotal();
        departmentDOList.forEach(departmentDO -> {
            departmentEntityList.add(DepartmentConvertor.toEntity(departmentDO));
        });
        return departmentEntityList;
    }

    @Override
    public DepartmentEntity findById(Long id) {
        Optional<DepartmentDO> findById = departmentMapper.selectById(id);
        if (findById.isEmpty()) {
            throw new WitBootBizException(DepartmentErrorCode.B_DEPARTMENT_UNDEFINED);
        }

        DepartmentDO departmentDO = findById.get();

        return DepartmentConvertor.toEntity(departmentDO);
    }

    /**
     * 删除用户
     *
     * @param ids ids
     * @return 删除
     */
    @Override
    public int deleteByIds(String[] ids){
        return departmentMapper.deleteByIds(ids);
    }
}
