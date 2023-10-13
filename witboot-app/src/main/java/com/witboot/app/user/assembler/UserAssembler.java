package com.witboot.app.user.assembler;

import com.witboot.client.user.dto.UserModifyCmd;
import com.witboot.client.user.dto.UserRegisterCmd;
import com.witboot.client.user.dto.data.UserVO;
import com.witboot.domain.user.model.UserEntity;
import com.witboot.domain.user.model.UserName;
import com.witboot.domain.user.model.UserPassword;

/**
 * User Application层 转换器
 * 用于DTO与实体之间的互转
 *
 * @author sunxiaozhi
 */
public class UserAssembler {
    public static UserEntity toEntity(UserRegisterCmd userRegisterCmd) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(new UserName(userRegisterCmd.getUsername()));
        userEntity.setPassword(new UserPassword(userRegisterCmd.getPassword()));
        userEntity.setName(userRegisterCmd.getName());
        userEntity.setMobile(userRegisterCmd.getMobile());
        userEntity.setGender(userRegisterCmd.getGender());
        userEntity.setBirthday(userRegisterCmd.getBirthday());
        userEntity.setDescription(userRegisterCmd.getDescription());

        return userEntity;
    }

    public static UserEntity toEntity(UserModifyCmd userModifyCmd) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userModifyCmd.getId());
        userEntity.setUsername(new UserName(userModifyCmd.getUsername()));
        userEntity.setName(userModifyCmd.getName());
        userEntity.setMobile(userModifyCmd.getPhoneNo());
        userEntity.setGender(userModifyCmd.getGender());
        userEntity.setBirthday(userModifyCmd.getBirthday());
        userEntity.setDescription(userModifyCmd.getDescription());

        return userEntity;
    }

    public static UserVO toValueObject(UserEntity userEntity) {
        UserVO userVO = new UserVO();
        userVO.setId(userEntity.getId());
        userVO.setName(userEntity.getName());
        userVO.setUsername(userEntity.getUsername().name());
        userVO.setMobile(userEntity.getMobile());
        userVO.setGender(userEntity.getGender());
        userVO.setBirthday(userEntity.getBirthday());
        userVO.setDescription(userEntity.getDescription());

        return userVO;
    }
}
