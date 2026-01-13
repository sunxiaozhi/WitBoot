package com.witboot.infrastructure.convertor;

import com.witboot.domain.department.model.DepartmentEntity;
import com.witboot.common.Constants;
import com.witboot.infrastructure.gateway.impl.database.dataobject.DepartmentDO;
import org.springframework.beans.BeanUtils;

/**
 * DepartmentConvertor DO <---> Entity
 *
 * @author sunxiaoizhi
 */
public class DepartmentConvertor {
    public static DepartmentEntity toEntity(DepartmentDO departmentDO) {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        /*departmentEntity.setId(departmentDO.getId());
        departmentEntity.setName(departmentDO.getName());
        departmentEntity.setParentId(departmentDO.getParentId());*/
        BeanUtils.copyProperties(departmentDO, departmentEntity);

        return departmentEntity;
    }

    public static DepartmentDO toAddDepartmentDO(DepartmentEntity departmentEntity) {
        DepartmentDO departmentDO = new DepartmentDO();
        departmentDO.setId(departmentEntity.getId());
        departmentDO.setName(departmentEntity.getName());
        departmentDO.setParentId(departmentEntity.getParentId());
        departmentDO.setDeleteFlag(Constants.NOT_DELETED_FLAG);
        departmentDO.setCreator("");
        departmentDO.setModifier("");

        return departmentDO;
    }

    public static void toModifyDepartmentDO(DepartmentEntity departmentEntity, DepartmentDO departmentDO) {
        departmentDO.setName(departmentEntity.getName());
        departmentDO.setParentId(departmentEntity.getParentId());
    }
}
