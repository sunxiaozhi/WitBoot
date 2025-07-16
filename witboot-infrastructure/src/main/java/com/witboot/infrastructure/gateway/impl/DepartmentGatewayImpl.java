package com.witboot.infrastructure.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.witboot.client.department.dto.data.DepartmentErrorCode;
import com.witboot.domain.base.model.PageResult;
import com.witboot.domain.department.gateway.DepartmentGateway;
import com.witboot.domain.department.model.DepartmentEntity;
import com.witboot.domain.department.query.DepartmentListByParamQuerySpec;
import com.witboot.infrastructure.common.exception.WitBootBizException;
import com.witboot.infrastructure.convertor.DepartmentConvertor;
import com.witboot.infrastructure.gateway.impl.database.dataobject.DepartmentDO;
import com.witboot.infrastructure.gateway.impl.database.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public PageResult<DepartmentEntity> findByParam(DepartmentListByParamQuerySpec departmentListByParamQuerySpec) {
        PageHelper.startPage(departmentListByParamQuerySpec.getPageNo(), departmentListByParamQuerySpec.getPageSize());
        List<DepartmentDO> departmentDOList = departmentMapper.selectByParam(departmentListByParamQuerySpec);
        PageInfo<DepartmentDO> pageInfo = new PageInfo<>(departmentDOList);

        List<DepartmentEntity> operationLogEntityList = departmentDOList.stream().map(DepartmentConvertor::toEntity).collect(Collectors.toList());

        return PageResult.build(operationLogEntityList, pageInfo.getTotal());
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
