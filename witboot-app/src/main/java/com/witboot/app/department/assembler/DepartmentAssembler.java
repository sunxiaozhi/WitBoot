package com.witboot.app.department.assembler;

import com.witboot.client.department.dto.data.DepartmentVO;
import com.witboot.domain.department.model.DepartmentEntity;

/**
 * Department Application层转换器
 * 用于DTO与实体之间的互转
 *
 * @author sunxiaozhi
 */
public class DepartmentAssembler {
    /*public static DepartmentEntity toEntity(DepartmentRegisterCmd departmentRegisterCmd) {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setDepartmentname(departmentRegisterCmd.getDepartmentname());
        departmentEntity.setPassword(new DepartmentPassword(departmentRegisterCmd.getPassword()));
        departmentEntity.setName(departmentRegisterCmd.getName());
        departmentEntity.setMobile(departmentRegisterCmd.getMobile());
        departmentEntity.setGender(departmentRegisterCmd.getGender());
        departmentEntity.setBirthday(departmentRegisterCmd.getBirthday());
        departmentEntity.setDescription(departmentRegisterCmd.getDescription());

        return departmentEntity;
    }

    public static DepartmentEntity toEntity(DepartmentModifyCmd departmentModifyCmd) {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(departmentModifyCmd.getId());
        departmentEntity.setDepartmentname(departmentModifyCmd.getDepartmentname());
        departmentEntity.setName(departmentModifyCmd.getName());
        departmentEntity.setMobile(departmentModifyCmd.getPhoneNo());
        departmentEntity.setGender(departmentModifyCmd.getGender());
        departmentEntity.setBirthday(departmentModifyCmd.getBirthday());
        departmentEntity.setDescription(departmentModifyCmd.getDescription());

        return departmentEntity;
    }*/

    public static DepartmentVO toValueObject(DepartmentEntity departmentEntity) {
        DepartmentVO departmentVO = new DepartmentVO();
        departmentVO.setId(departmentEntity.getId());
        departmentVO.setName(departmentEntity.getName());
        departmentVO.setParentId(departmentEntity.getParentId());

        return departmentVO;
    }
}
