package com.witboot.app.user.assembler;

import com.witboot.client.user.dto.UserUpdateCmd;
import com.witboot.client.user.dto.UserRegisterCmd;
import com.witboot.client.user.dto.data.UserVO;
import com.witboot.domain.user.model.UserEntity;
import com.witboot.domain.user.model.UserPassword;

/**
 * User Application层转换器
 * 用于DTO与实体之间的互转
 *
 * @author sunxiaozhi
 */
public class UserAssembler {
    public static UserEntity toEntity(UserRegisterCmd userRegisterCmd) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRegisterCmd.getUsername());
        userEntity.setPassword(new UserPassword(userRegisterCmd.getPassword()));
        userEntity.setName(userRegisterCmd.getName());
        userEntity.setMobile(userRegisterCmd.getMobile());
        userEntity.setGender(userRegisterCmd.getGender());
        userEntity.setBirthday(userRegisterCmd.getBirthday());
        userEntity.setDescription(userRegisterCmd.getDescription());

        return userEntity;
    }

    public static UserEntity toEntity(UserUpdateCmd userUpdateCmd) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userUpdateCmd.getId());
        userEntity.setUsername(userUpdateCmd.getUsername());
        userEntity.setName(userUpdateCmd.getName());
        userEntity.setMobile(userUpdateCmd.getPhoneNo());
        userEntity.setGender(userUpdateCmd.getGender());
        userEntity.setBirthday(userUpdateCmd.getBirthday());
        userEntity.setDescription(userUpdateCmd.getDescription());

        return userEntity;
    }

    public static UserVO toValueObject(UserEntity userEntity) {
        UserVO userVO = new UserVO();
        userVO.setId(userEntity.getId());
        userVO.setName(userEntity.getName());
        userVO.setUsername(userEntity.getUsername());
        userVO.setMobile(userEntity.getMobile());
        userVO.setGender(userEntity.getGender());
        userVO.setBirthday(userEntity.getBirthday());
        userVO.setDescription(userEntity.getDescription());

        return userVO;
    }
}
