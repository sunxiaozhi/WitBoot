package com.witboot.infrastructure.convertor;

import com.witboot.domain.department.model.DepartmentEntity;
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
        departmentEntity.setId(departmentDO.getId());
        departmentEntity.setName(departmentDO.getName());
        departmentEntity.setParentId(departmentDO.getParentId());
        /*departmentEntity.setId(departmentDO.getId());
        departmentEntity.setDepartmentname(departmentDO.getDepartmentname());
        departmentEntity.setName(departmentDO.getName());
        departmentEntity.setMobile(departmentDO.getMobile());
        departmentEntity.setGender(departmentDO.getGender());
        departmentEntity.setBirthday(departmentDO.getBirthday());
        departmentEntity.setDescription(departmentDO.getDescription());*/
        BeanUtils.copyProperties(departmentDO, departmentEntity);

        return departmentEntity;
    }

    public static DepartmentDO toAddDepartmentDO(DepartmentEntity departmentEntity) {
        DepartmentDO departmentDO = new DepartmentDO();
        departmentDO.setId(departmentEntity.getId());
//        departmentDO.setDepartmentname(departmentEntity.getDepartmentname());
//        departmentDO.setPassword(departmentEntity.getPassword().getEncryptPassword());
        departmentDO.setName(departmentEntity.getName());
        departmentDO.setDeleteFlag(1);
//        departmentDO.setMobile(departmentEntity.getMobile());
//        departmentDO.setGender(departmentEntity.getGender());
//        departmentDO.setBirthday(departmentEntity.getBirthday());
//        departmentDO.setDescription(departmentEntity.getDescription());
        departmentDO.setCreator("");
        departmentDO.setModifier("");

        return departmentDO;
    }

    public static void toModifyDepartmentDO(DepartmentEntity departmentEntity, DepartmentDO departmentDO) {
        departmentDO.setName(departmentEntity.getName());
//        departmentDO.setDepartmentname(departmentEntity.getDepartmentname());
//        departmentDO.setMobile(departmentEntity.getMobile());
//        departmentDO.setGender(departmentEntity.getGender());
//        departmentDO.setBirthday(departmentEntity.getBirthday());
//        departmentDO.setDescription(departmentEntity.getDescription());
    }
}
