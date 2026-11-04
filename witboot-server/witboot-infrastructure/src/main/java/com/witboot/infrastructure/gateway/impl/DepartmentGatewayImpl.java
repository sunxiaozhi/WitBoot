package com.witboot.infrastructure.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.witboot.client.department.dto.data.DepartmentErrorCode;
import com.witboot.common.Constants;
import com.witboot.common.core.model.PageResult;
import com.witboot.domain.department.gateway.DepartmentGateway;
import com.witboot.domain.department.model.DepartmentEntity;
import com.witboot.domain.department.query.DepartmentQuerySpec;
import com.witboot.common.exception.WitBootBizException;
import com.witboot.infrastructure.convertor.DepartmentConvertor;
import com.witboot.infrastructure.gateway.impl.database.dataobject.DepartmentDO;
import com.witboot.infrastructure.gateway.impl.database.mapper.DepartmentMapper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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
    public PageResult<DepartmentEntity> findByParam(DepartmentQuerySpec departmentListByParamQuerySpec) {
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

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public DepartmentEntity save(DepartmentEntity departmentEntity) {
        if (Objects.isNull(departmentEntity.getId())) {
            return addDepartment(departmentEntity);
        }

        return updateDepartment(departmentEntity);
    }

    /**
     * 新增部门
     */
    private DepartmentEntity addDepartment(DepartmentEntity departmentEntity) {
        DepartmentDO departmentDO = DepartmentConvertor.toAddDepartmentDO(departmentEntity);

        int insert = departmentMapper.insert(departmentDO);
        if (insert < Constants.ONE) {
            throw new PersistenceException("保存部门异常");
        }

        return DepartmentConvertor.toEntity(departmentDO);
    }

    /**
     * 修改部门
     */
    private DepartmentEntity updateDepartment(DepartmentEntity departmentEntity) {
        Optional<DepartmentDO> findById = departmentMapper.selectById(departmentEntity.getId());
        if (findById.isEmpty()) {
            throw new WitBootBizException(DepartmentErrorCode.B_DEPARTMENT_UNDEFINED);
        }

        DepartmentDO departmentDO = findById.get();
        DepartmentConvertor.toModifyDepartmentDO(departmentEntity, departmentDO);

        int update = departmentMapper.update(departmentDO);
        if (update < Constants.ONE) {
            throw new PersistenceException("更新部门异常");
        }

        return DepartmentConvertor.toEntity(departmentDO);
    }

    /**
     * 删除部门
     *
     * @param ids ids
     * @return 删除
     */
    @Override
    public int deleteByIds(String[] ids){
        return departmentMapper.deleteByIds(ids);
    }
}
